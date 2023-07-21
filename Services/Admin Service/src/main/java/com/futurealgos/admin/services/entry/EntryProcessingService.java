package com.futurealgos.admin.services.entry;

import com.futurealgos.admin.dto.payload.entry.EntryPayload;
import com.futurealgos.admin.dto.response.entry.EntryInfo;
import com.futurealgos.admin.exception.exceptions.AlreadyExistsException;
import com.futurealgos.admin.exception.exceptions.UnauthorizedException;
import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.secondary.entry.*;
import com.futurealgos.admin.repos.primary.EntryRepository;
import com.futurealgos.admin.repos.primary.StagedEntryRepository;
import com.futurealgos.admin.repos.secondary.DowntimeRepository;
import com.futurealgos.admin.repos.secondary.ProductionRepository;
import com.futurealgos.admin.repos.secondary.RejectionRepository;
import com.futurealgos.admin.services.area.TemplateService;
import com.futurealgos.admin.services.products.ProductService;
import com.futurealgos.admin.services.products.VariantService;
import com.futurealgos.admin.utils.constants.ConstantService;
import com.futurealgos.admin.utils.enums.Sign;
import com.futurealgos.specs.utils.SearchFilter;
import liquibase.database.core.SybaseDatabase;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EntryProcessingService {

    final
    StagedEntryRepository stagedEntryRepository;

    StagedEntryService stagedEntryService;
    final
    EntryRepository entryRepository;

    EntryService entryService;
    final
    ProductService productService;
    final
    VariantService variantService;
    final
    TemplateService templateService;
    final ProductionRepository productionRepository;
    final RejectionRepository rejectionRepository;

    final DowntimeRepository downtimeRepository;

    final ConstantService constants;

    public EntryProcessingService(StagedEntryRepository stagedEntryRepository,
                                  EntryService entryService,
                                  EntryRepository entryRepository,
                                  ProductService productService,
                                  VariantService variantService,
                                  TemplateService templateService,
                                  DowntimeRepository downtimeRepository,
                                  ProductionRepository productionRepository,
                                  RejectionRepository rejectionRepository,
                                  StagedEntryService stagedEntryService,
                                  ConstantService constants) {
        this.stagedEntryRepository = stagedEntryRepository;
        this.entryService = entryService;
        this.entryRepository = entryRepository;
        this.productionRepository = productionRepository;
        this.rejectionRepository = rejectionRepository;
        this.downtimeRepository = downtimeRepository;
        this.productService = productService;
        this.variantService = variantService;
        this.templateService = templateService;
        this.stagedEntryService = stagedEntryService;
        this.constants = constants;
    }

    @Transactional
    public UUID submit(StagedEntry payload, String admin) {
        var variants = variantService.fetchAll(payload.getVariants());
        if (entryService.alreadyExists(payload.shiftDate, payload.machine, payload.shift)) {
            throw new AlreadyExistsException("Entry Already Exists");
        }

        LocalTime time  =  payload.getShift().stopTime.minusMinutes(30);
        LocalDateTime dateAndTime =  LocalDateTime.of(payload.shiftDate, time);
        if (payload.getShift().spanToNextDay) {
            dateAndTime = dateAndTime.plusDays(1);
        }
//        if (LocalDateTime.now(ZoneId.of("Asia/Kolkata")).isBefore(dateAndTime.minusHours(5).minusMinutes(30))){
//            throw new AlreadyExistsException(payload.getShift().name+" Has Not Been Ended Yet");
//        }
        UUID uuid = UUID.randomUUID();

        var entry = Entry.builder()
                .id(uuid)
                .shift(payload.shift)
                .shiftDate(payload.getShiftDate())
                .product(payload.getProduct())
                .variant(variants)
                .expectedMachineSpeed(payload.getMachine().getSpeed())
                .machine(payload.getMachine())
                .operator(payload.getOperator())
                .oeeTarget(payload.getMachine().getOeeTarget())
                .lsaTarget((double)payload.machine.getLsaTarget())
                .remarks(payload.getRemarks())
                .build();

        entry = entryRepository.save(entry, admin);

        mapDowntime(payload, entry, admin);
        mapProductionAndRejection(payload, entry, admin);
        calculateParameters(entry);
        entryRepository.save(entry, admin);

        if (payload.getId() != null) {
            stagedEntryRepository.delete(payload);
        }
        return uuid;
    }


    public void mapDowntime(StagedEntry payload, Entry entry, String admin) {

        entry.setDowntimes(payload.getDowntimes().stream().map(shell -> {
            var downtime = Downtime.builder()
                    .reason(shell.getReason())
                    .reasonType(Downtime.ReasonType.CONNECTED)
                    .time(shell.getTime())
                    .type(shell.getType())
                    .entry(entry)
                    .association(shell.getAssociation() != null ? shell.getAssociation() : payload.getMachine().getId())
                    .build();

            downtime.determineCategory(shell);
            downtimeRepository.save(downtime, admin);
            return downtime;
        }).collect(Collectors.toSet()));
    }

    private Production buildProduction(Template template, Double value, Entry entry) {

        String oldDesc = template.getDescription();
        String desc = oldDesc.replace("(Kg)", "");
        desc = desc.replace("(KG)", "");
        desc = desc.trim();
        return Production.builder()
                .value(value)
                .description(desc)
                .sign(template.getSign())
                .template(template)
                .entry(entry)
                .build();
    }

    private Rejection buildRejection(Template template, Double value, Entry entry) {
        String oldDecs = template.getDescription();
        String desc = oldDecs.replace("(Kg)", "");
        desc = desc.replace("(KG)", "");
        desc = desc.trim();
        return Rejection.builder()
                .value(value)
                .description(desc)
                .sign(template.getSign())
                .template(template)
                .entry(entry)
                .build();
    }

    public void mapProductionAndRejection(StagedEntry payload, Entry entry, String admin) {
        if (payload.quantities.isEmpty()) {
            entry.setProductions(new ArrayList<>());
            entry.setRejections(new ArrayList<>());
            entry.setTotalProduction(0.0);
            entry.setTotalRejection(0.0);
            return;
        }

        Double total = 0.0;
        Double extras = 0.0;
        Double tProd = 0.0;
        Double tRej = 0.0;

        List<Production> totalProductions = new ArrayList<>();
        List<Production> extraProductions = new ArrayList<>();
        List<Production> productions = new ArrayList<>();
        List<Rejection> rejections = new ArrayList<>();

        for (StagedTemplate quant : payload.quantities) {
            Template template = quant.getTemplate();
            Double finalValue = quant.value;

            if (template.getFormula() != null) {
                int multiplier = template.getFormula().isInKg() ? 1000 : 1;
                if (template.getFormula().getType().equals(Formula.Type.EXPRESSION)) {
                    finalValue = template.getFormula().getOperation().equals(Formula.Operation.DIVIDE) ?
                            (quant.value * multiplier) / payload.getProduct().getWeight() : quant.value * payload.getProduct().getWeight();
                } else {
                    finalValue = template.getFormula().getOperation().equals(Formula.Operation.DIVIDE) ?
                            (quant.value * multiplier) / template.getFormula().getOperand() : quant.value * template.getFormula().getOperand();
                }

                finalValue = ((Long) Math.round(finalValue)).doubleValue();
            }

            switch (quant.type) {
                case PRODUCTION -> {
                    Production production = buildProduction(template, finalValue, entry);
                    production.setActualValue(quant.value);
                    tProd = template.getSign().equals(Sign.ADD) ? tProd + finalValue : template.getSign().equals(Sign.SUB) ? tProd - finalValue : tProd;
                    productions.add(production);
                }
                case REJECTION -> {
                    Rejection rejection = buildRejection(template, finalValue, entry);
                    rejection.setActualValue(quant.value);
                    tRej = template.getSign().equals(Sign.ADD) ? tRej + finalValue : template.getSign().equals(Sign.SUB) ? tRej - finalValue : tRej;
                    rejections.add(rejection);
                }
                case TOTAL -> {
                    if (!totalProductions.isEmpty())
                        throw new IllegalStateException("There can only be 1 template for Total Production");

                    Production totalProduction = buildProduction(template, finalValue, entry);
                    totalProduction.setActualValue(quant.value);
                    total = template.getSign().equals(Sign.ADD) ? total + finalValue : template.getSign().equals(Sign.SUB) ? total - finalValue : total;
                    totalProductions.add(totalProduction);
                }
                case EXTRA -> {
                    Production extraProduction = buildProduction(template, finalValue, entry);
                    extraProduction.setActualValue(quant.value);
                    extras = template.getSign().equals(Sign.ADD) ? extras + finalValue : template.getSign().equals(Sign.SUB) ? extras - finalValue : extras;
                    extraProductions.add(extraProduction);
                }
            }

        }

        if (productions.isEmpty()) {
            if (totalProductions.isEmpty())
                throw new IllegalStateException("Either Total Production or Actual Production has to be entered");

            tProd = total - extras;
            tRej = tRej + extras;
            Production totalProduction = totalProductions.get(0);
            productions.add(Production.builder()
                    .sign(totalProduction.sign)
                    .description("Total Production")
                    .value(total)
                    .entry(entry)
                    .build());

            productions.add(Production.builder()
                    .sign(Sign.ADD)
                    .description("Good Production")
                    .value(tProd)
                    .entry(entry)
                    .build());

            productions.addAll(extraProductions);

            entry.setProductions(productions);
            entry.setRejections(rejections);
            entry.setTotalProduction(total);
        } else {
            if (!extraProductions.isEmpty()) {
                tProd -= extras;
                tRej += extras;
            }

            tProd = tProd < 0 ? 0 : tProd;
            productions.addAll(extraProductions);

            entry.setProductions(productions);
            entry.setRejections(rejections);

            entry.setTotalProduction(tProd + tRej);
        }
        entry.setGoodProduction(tProd);
        entry.setTotalRejection(tRej);

        entry.getProductions().forEach(production -> {
            production.setEntry(entry);
            productionRepository.save(production, admin);
        });

        entry.getRejections().forEach(rejection -> {
            rejection.setEntry(entry);
            rejectionRepository.save(rejection, admin);
        });
    }

    public void calculateParameters(Entry entry) {
        entry.refreshOee(constants);
    }

    @Transactional
    public EntryInfo update(String id, EntryPayload payload, String admin) {
        Entry entry = entryService.fetch(id);

        //removed Previous Production , rejections and downtime
        rejectionRepository.deleteAll(entry.getRejections());
        productionRepository.deleteAll(entry.getProductions());
        downtimeRepository.deleteAll(entry.getDowntimes());

        entry.setProductions(new ArrayList<>());
        entry.setRejections(new ArrayList<>());
        entry.setDowntimes(new HashSet<>());
        entry = entryRepository.save(entry, admin);


        //created new staged Entry

        StagedEntry stagedEntry = StagedEntry.builder().build();

        stagedEntryService.buildBasic(payload, stagedEntry, admin);
        stagedEntry.setVariants(payload.variants());

        //calls old methods
        mapDowntime(stagedEntry, entry, admin);
        mapProductionAndRejection(stagedEntry, entry, admin);
        calculateParameters(entry);

        //saved new entry
       return  (entryRepository.save(entry, admin)).info();
    }


}
