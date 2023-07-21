package com.futurealgos.admin.services.entry;

import com.futurealgos.admin.dto.payload.entry.EntryPayload;
import com.futurealgos.admin.dto.payload.entry.StagedBreakdownPayload;
import com.futurealgos.admin.dto.payload.entry.StagedTemplatePayload;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.models.secondary.entry.StagedBreakdown;
import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import com.futurealgos.admin.models.secondary.entry.StagedTemplate;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.repos.primary.StagedEntryRepository;
import com.futurealgos.admin.repos.secondary.StagedBreakdownRepository;
import com.futurealgos.admin.repos.secondary.StagedTemplateRepository;
import com.futurealgos.admin.services.administration.ShiftService;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.area.TemplateService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.products.ProductService;
import com.futurealgos.admin.services.reasons.ReasonService;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.service.ServiceTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StagedEntryService extends ServiceTemplate<StagedEntry, UUID, EntryPayload, EntryMinimal,EntryMinimal, EntryPayload, EntryPayload> {

    StagedEntryRepository stagedEntryRepository;
    final AreaService areaService;
    final ShiftService shiftService;
    final MachineService machineService;
    final UserService userService;
    final ProductService productService;
    final TemplateService templateService;
    final StagedBreakdownRepository stagedBreakDownRepository;
    final StagedTemplateRepository stagedTemplateRepository;
    final ReasonService reasonService;

    public StagedEntryService(StagedEntryRepository repository,
                              AreaService areaService,
                              ShiftService shiftService,
                              MachineService machineService,
                              UserService userService,
                              ProductService productService,
                              TemplateService templateService,
                              StagedBreakdownRepository stagedBreakDownRepository,
                              StagedTemplateRepository stagedTemplateRepository,
                              ReasonService reasonService) {
        super(StagedEntry.class, repository, repository);
        this.stagedEntryRepository = repository;
        this.areaService = areaService;
        this.shiftService = shiftService;
        this.machineService = machineService;
        this.userService = userService;
        this.productService = productService;
        this.templateService = templateService;
        this.stagedBreakDownRepository = stagedBreakDownRepository;
        this.stagedTemplateRepository = stagedTemplateRepository;
        this.reasonService = reasonService;
    }

    @Override
    public StagedEntry update(String id, EntryPayload payload, String admin, Date timestamp) {
        StagedEntry stagedEntry = fetch(UUID.fromString(id));
        stagedTemplateRepository.deleteAll(stagedEntry.getQuantities());
        stagedBreakDownRepository.deleteAll(stagedEntry.getDowntimes());

        buildBasic(payload, stagedEntry, admin);
        stagedEntry.setVariants(payload.variants());

        stagedEntry.getDowntimes().stream().map(br -> stagedBreakDownRepository.save(br, admin)).toList();
        stagedEntry.getQuantities().stream().map(st -> stagedTemplateRepository.save(st, admin)).toList();

        stagedEntry = stagedEntryRepository.save(stagedEntry, admin, timestamp);
        return stagedEntry;
    }

    public void buildBasic(EntryPayload payload, StagedEntry stagedEntry, String admin) {
        stagedEntry.populate(
                payload.area() != null ? areaService.fetch(payload.area()) : null,
                payload.product() != null ? productService.fetch(payload.product()) : null,
                LocalDate.parse(payload.date(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                payload.remarks(),
                machineService.fetch(payload.machine()),
                payload.operator() != null ? userService.fetch(payload.operator()) : null,
                payload.shift() != null ? shiftService.fetch(payload.shift()) : null,
                payload.variants()
        );

        stagedEntry.setDowntimes(new HashSet<>(buildStagedBreakdown(stagedEntry, payload.downtimes(), admin)));
        stagedEntry.setQuantities(new HashSet<>(buildStagedTemplate(stagedEntry, payload.quantities(), admin)));
    }

    @Override
    public StagedEntry create(EntryPayload payload, String admin, Date timestamp) {
        StagedEntry stagedEntry = StagedEntry.builder().build();
        stagedEntry = stagedEntryRepository.save(stagedEntry, admin, timestamp);
        buildBasic(payload, stagedEntry, admin);
        stagedEntry.getDowntimes().stream().map(br -> stagedBreakDownRepository.save(br, admin)).toList();
        stagedEntry.getQuantities().stream().map(st -> stagedTemplateRepository.save(st, admin)).toList();
        stagedEntry = stagedEntryRepository.save(stagedEntry, admin, timestamp);

        return stagedEntry;
    }

    public List<StagedBreakdown> buildStagedBreakdown(StagedEntry stagedEntry, List<StagedBreakdownPayload> payloads, String admin) {
        List<StagedBreakdown> stagedBreakdowns = new ArrayList<>();

        if (!payloads.isEmpty()) {
            for (StagedBreakdownPayload payload : payloads) {
                StagedBreakdown breakdown = StagedBreakdown.builder()
                        .id(UUID.randomUUID())
                        .reason(reasonService.fetch(payload.reason()))
                        .type(BreakdownType.valueOf(payload.type()))
                        .time(payload.time())
                        .manualCategory(payload.manualCategory() != null && !payload.manualCategory().isBlank() ? UUID.fromString(payload.manualCategory()) : null)
                        .association(payload.association() != null ? UUID.fromString(payload.association()) : null)
                        .entry(stagedEntry)
                        .build();

                stagedBreakdowns.add(breakdown);
            }

        }

        return stagedBreakdowns;
    }

    public List<StagedTemplate> buildStagedTemplate(StagedEntry stagedEntry, List<StagedTemplatePayload> payloads, String admin) {
        List<StagedTemplate> stagedTemplates = new ArrayList<>();

        for (StagedTemplatePayload payload : payloads) {
            Template template = templateService.fetch(payload.template());
            StagedTemplate stagedTemplate = StagedTemplate.builder()
                    .id(UUID.randomUUID())
                    .template(template)
                    .type(Template.Type.valueOf(payload.type()))
                    .value(payload.value())
                    .entry(stagedEntry)
                    .build();

            stagedTemplates.add(stagedTemplate);
        }

        return stagedTemplates;
    }



}
