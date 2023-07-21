package com.futurealgos.admin.services.entry;

import com.futurealgos.admin.dto.payload.entry.EntryPayload;
import com.futurealgos.admin.dto.payload.entry.MachineEntryInfo;
import com.futurealgos.admin.dto.response.entry.EntryInfo;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.machine.*;
import com.futurealgos.admin.dto.response.users.EntryDetails;
import com.futurealgos.admin.models.primary.*;
import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import com.futurealgos.admin.repos.primary.EntryRepository;
import com.futurealgos.admin.repos.primary.StagedEntryRepository;
import com.futurealgos.admin.repos.primary.UserRepository;
import com.futurealgos.admin.repos.secondary.DowntimeRepository;
import com.futurealgos.admin.repos.secondary.StationRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@Service
public class EntryService extends ServiceTemplate<Entry, UUID, EntryInfo, EntryMinimal, EntryMinimal, EntryPayload, EntryPayload> {
    EntryRepository entryRepository;

    @Autowired
    DowntimeRepository downtimeRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    StagedEntryRepository stagedEntryRepository;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;

    public EntryService(EntryRepository entryRepository,
                        StationRepository stationRepository,
                        UserRepository userRepository) {
        super(Entry.class, entryRepository, entryRepository);
        this.entryRepository = entryRepository;
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
    }

    private double roundOff(double val) {
        return Math.round(val * 100.0) / 100.0;
    }


    public EntryPayload fetchEditPayload(String id) {
        return EntryPayload.from(fetch(id));
    }

    public ParetoData getParetoData(Machine machine, LocalDate start, LocalDate end) {
        List<ParetoQuery> queries = downtimeRepository.getParetoData(machine, start, end);
        ParetoData data = new ParetoData();
        float total = 0;
        if (queries.size() == 0) return data;
        for (ParetoQuery query : queries) {
            data.reasons.add(query.getReason().getReason());
            data.downtime.add(query.getDowntime());
            total += query.getDowntime();
        }
        double percent = (((double) data.downtime.get(0) * 100) / total);
        percent = roundOff(percent);

        data.percentage.add(percent);

        data.cumulativePercentage.add(percent);

        for (int i = 1; i < queries.size(); i++) {
            percent = (data.downtime.get(i) * 100) / total;
            percent = roundOff(percent);
            data.percentage.add(percent);
            percent = data.cumulativePercentage.get(i - 1) + percent;
            percent = roundOff(percent);
            percent = Math.min(percent, 100.0);
            data.cumulativePercentage.add(percent);
        }

        data.shifts = entryRepository.countByMachineAndShiftDateBetween(machine, start, end);

        return data;
    }
    

    public BOSData getBOSData(Machine machine, LocalDate start, LocalDate end) {
        List<BOSQuery> queries = entryRepository.getBOSData(machine, start, end);
        BOSData data = new BOSData();

        for (BOSQuery query : queries) {

            LocalDate startDate = LocalDate.parse(query.getTstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LocalDate endDate = LocalDate.parse(query.getTend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LocalDate startingLabel = startDate.with(previousOrSame(DayOfWeek.SUNDAY));
            LocalDate endingLabel = endDate.with(nextOrSame(DayOfWeek.SATURDAY));

            if (startingLabel.isBefore(start)) startingLabel = start;

            if (endingLabel.isAfter(end)) endingLabel = end;


            data.labels.add(startingLabel.format(DateTimeFormatter.ofPattern("dd MMM")) + "-" + endingLabel.format(DateTimeFormatter.ofPattern("dd MMM yy")));

            data.goodQuantities.add(query.getQty());

            data.lsa.add((long) machine.getLsaTarget());
            data.stretched.add((long) machine.getStretchedTarget());
            data.weekly.add(query.getQty() / query.getShifts());
        }

        Queue<Long> w4 = new LinkedList<>();
        Queue<Long> w12 = new LinkedList<>();
        long ws4 = 0;
        long ws12 = 0;
        for (int i = 0; i < data.weekly.size(); i++) {
            long d = data.weekly.get(i);
            ws4 += d;
            ws12 += d;

            w4.add(d);
            w12.add(d);

            if (w12.size() > 12) {
                ws12 -= w12.remove();
            }

            if (w4.size() > 4) {
                ws4 -= w4.remove();
            }

            data.weekly4.add(ws4 / w4.size());
            data.weekly12.add(ws12 / w12.size());


        }


        return data;
    }

    public Page<StagedEntry> pendingList(Pageable pageable) {
        return stagedEntryRepository.findAll(pageable);
    }

    @Override
    public EntryInfo info(String id) throws NotFoundException {
        Entry entry = fetch(id);
        return EntryInfo.fromShiftEntry(entry);
    }

    public boolean alreadyExists(LocalDate shiftDate, Machine machine, Shift shift) {
        return entryRepository.existsByShiftAndShiftDateAndMachine(shift, shiftDate, machine);
    }

    public OeeBOSData oeeBOSData(Machine machine, LocalDate start, LocalDate end) {
        List<OeeBOSQuery> queries = entryRepository.getOEEBOSData(machine, start, end);
        OeeBOSData data = new OeeBOSData();
        BOSData productionBos = getBOSData(machine, start, end);
        for (OeeBOSQuery query : queries) {
            LocalDate startDate = LocalDate.parse(query.getTstart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LocalDate endDate = LocalDate.parse(query.getTend().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LocalDate startingLabel = startDate.with(previousOrSame(DayOfWeek.SUNDAY));
            LocalDate endingLabel = endDate.with(nextOrSame(DayOfWeek.SATURDAY));

            if (startingLabel.isBefore(start)) startingLabel = start;


            if (endingLabel.isAfter(end)) endingLabel = end;


            data.labels.add(startingLabel.format(DateTimeFormatter.ofPattern("dd MMM")) + "-" + endingLabel.format(DateTimeFormatter.ofPattern("dd MMM yy")));
            data.oeeTarget.add(query.getOeeTarget());
            data.baseValue.add(machine.getBaseValue());

            data.weekly.add((double) Math.round(query.getOee() * 100) / 100);
        }

        Queue<Double> w4 = new LinkedList<>();
        Queue<Double> w12 = new LinkedList<>();
        long ws4 = 0;
        long ws12 = 0;
        for (int i = 0; i < data.weekly.size(); i++) {
            double d = data.weekly.get(i);
            ws4 += d;
            ws12 += d;

            w4.add(d);
            w12.add(d);

            if (w12.size() > 12) {
                ws12 -= w12.remove();
            }

            if (w4.size() > 4) {
                ws4 -= w4.remove();
            }

            data.weekly4.add(Math.round(ws4 / (double) w4.size() * 100.00) / 100.00);
            data.weekly12.add(Math.round(ws12 / (double) w12.size() * 100.00) / 100.00);
            data.setLsa(productionBos.weekly12);

        }

        return data;

    }

    public OeePareto getOeeParetoData(Machine machine, LocalDate start, LocalDate end) {
        List<OeeParetoQuery> queries = downtimeRepository.getOeeParetoData(machine, start, end);
        OeePareto data = new OeePareto();
        float total = 0;
        if (queries.size() == 0) return data;
        for (OeeParetoQuery query : queries) {
            data.category.add(query.getCategory().getName());
            data.downtime.add(query.getDowntime());
            total += query.getDowntime();
        }
        double percent = (((double) data.downtime.get(0) * 100) / total);
        percent = roundOff(percent);

        data.percentage.add(percent);

        data.cumulativePercentage.add(percent);

        for (int i = 1; i < queries.size(); i++) {
            percent = (data.downtime.get(i) * 100) / total;
            percent = roundOff(percent);
            data.percentage.add(percent);
            percent = data.cumulativePercentage.get(i - 1) + percent;
            percent = roundOff(percent);
            percent = Math.min(percent, 100.0);
            data.cumulativePercentage.add(percent);
        }

        data.shifts = entryRepository.countByMachineAndShiftDateBetween(machine, start, end);

        return data;
    }

    public List<EntryDetails> userLatestEntry(User user) {
        return entryRepository.findByOperator(user).stream().map(EntryDetails::convert).toList();
    }

    public Entry lastEntry(User user) {

        return entryRepository.findFirstByOperatorOrderByShiftDateDesc(user).orElse(null);
    }

    public Entry findByAreaAndDate(Area area, LocalDate shiftDate) {

        return entryRepository.findFirstByShift_AreaAndShiftDateOrderByOeeDesc(area, shiftDate);
    }
    public Entry findLastEntryByArea(Area area){
        return  entryRepository.findFirstByShift_AreaOrderByShiftDateDesc(area);
    }

    public List<EntryMinimal> lastFiveEntries(User user) {
        return entryRepository.findTop5ByOperatorOrderByShiftDateDesc(user).stream().map(Entry::minimal).toList();

    }

    public List<MachineEntryInfo> topPerformingMachines() {
        List<MachineEntryInfo> infoList = new ArrayList<>();
        List<Entry> entryList = entryRepository.findByShiftDate(LocalDate.now().minusDays(1));
        Map<Area, Entry> entryAreaMap = entryList.stream()
                .collect(Collectors.toMap(a -> a.getMachine().getArea(), Function.identity(), BinaryOperator.maxBy(Comparator.comparingDouble(Entry::getOee)))
                );

        entryAreaMap.forEach((k, v) -> {
                    infoList.add(MachineEntryInfo.convert(v));
                }
        );
        return infoList;

    }

    public List<EntryMinimal> last3Entries() {
        List<Entry> entries = entryRepository.findTop3ByOrderByCreatedOnDesc();
        return entries.stream().map(Entry::minimal).toList();

    }

    public List<Entry> monthlyMachineEntry(Machine machine, LocalDate start, LocalDate end) {
        return entryRepository.findByMachineAndShiftDateBetween(machine, start, end);
    }

    public MachineEntryInfo areaTopPerformingMachine(String id) {
        Area area = areaService.fetch(id);
        Entry lastEntry = findLastEntryByArea(area);
        return MachineEntryInfo.convert(findByAreaAndDate(area,lastEntry.getShiftDate()));
    }
}
