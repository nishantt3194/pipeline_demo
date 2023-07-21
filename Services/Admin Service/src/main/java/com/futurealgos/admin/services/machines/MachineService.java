package com.futurealgos.admin.services.machines;

import com.futurealgos.admin.dto.payload.entry.MachineEntryInfo;
import com.futurealgos.admin.dto.payload.machines.MachineEdit;
import com.futurealgos.admin.dto.payload.machines.NewMachine;
import com.futurealgos.admin.dto.response.entry.CumulativeData;
import com.futurealgos.admin.dto.response.machine.MachineInfo;
import com.futurealgos.admin.dto.response.machine.MachineMinimal;
import com.futurealgos.admin.dto.response.machine.MachineSearch;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.repos.primary.EntryRepository;
import com.futurealgos.admin.repos.primary.MachineRepository;
import com.futurealgos.admin.repos.secondary.PrecheckRepository;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.entry.EntryService;
import com.futurealgos.admin.services.holiday.HolidayService;
import com.futurealgos.admin.utils.converters.MachineCreator;
import com.futurealgos.data.service.ServiceTemplate;
import com.futurealgos.specs.utils.SearchFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MachineService extends ServiceTemplate<Machine, UUID, MachineInfo, MachineMinimal, MachineSearch, NewMachine, MachineEdit> {

    MachineRepository machineRepository;
    HolidayService holidayService;
    EntryService entryService;

    StationService stationService;

    PrecheckService precheckService;
    private final PrecheckRepository precheckRepository;
    private final EntryRepository entryRepository;

    public MachineService(MachineRepository repository, AreaService areaService,
                          EntryService entryService,
                          HolidayService holidayService,
                          StationService stationService,
                          @Lazy PrecheckService precheckService,
                          PrecheckRepository precheckRepository,
                          EntryRepository entryRepository) {
        super(Machine.class, repository, repository);
        Assert.notNull(areaService, "Area Service should not be null");
        this.machineRepository = repository;
        this.holidayService = holidayService;
        this.stationService = stationService;
        this.precheckService = precheckService;
        this.entryService = entryService;
        this.converter = new MachineCreator(areaService);
        this.precheckRepository = precheckRepository;
        this.entryRepository = entryRepository;
    }


    public Double utilizationPercetage(String machineId, String startDate, String endDate) {

        Machine machine = fetch(machineId);

        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(SearchFilter.is("machine", machine));

        LocalDate start_Date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).minusDays(1);
        LocalDate end_Date = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).plusDays(1);


        filters.add(SearchFilter.isAfter("shiftDate", start_Date));
        filters.add(SearchFilter.isBefore("shiftDate", end_Date));

        List<Entry> entries = entryService.searchElements(filters.toArray(SearchFilter[]::new));
        long scheduleProductionTime = entries.size();
        Double totalUtilizationLoss = (entries.stream().map(Entry::utilizationLoss)
                .reduce(0.00, (initial, result) -> result + initial));

        long numberOfDaysSelected = ChronoUnit.DAYS.between(start_Date, end_Date);
        long holidays = holidayService.countByDateBetween(start_Date, end_Date);
        long allTime = (numberOfDaysSelected - holidays) * 3;

        return ((double) scheduleProductionTime / (allTime - totalUtilizationLoss)) * 100;
    }

    public MachineEntryInfo topPerformingMachine(User user) {
        Entry entry = entryService.lastEntry(user);
        if (entry != null) {
            Entry newEntry = entryService.findByAreaAndDate(entry.getShift().getArea(), entry.getShiftDate());
            return MachineEntryInfo.convert(newEntry);
        }
        else return null;
    }

    @Override
    public MachineInfo update(String id, MachineEdit payload, String admin) {
        Machine machine = fetch(id);
        machine.setBaseValue(payload.baseValue());
        machine.setLsaTarget(payload.lsaTarget());
        machine.setOeeTarget(payload.oeeTarget());
        machine.setProdLimit(payload.prodLimit());
        machine.setSpeed(payload.speed());
        machine.setTolerance(payload.tolerance());
        machine.setStretchedTarget(payload.stretchedTarget());

        return machineRepository.save(machine).info();

    }

    public CumulativeData cummulativeData(String machineId) {
        Machine machine = fetch(machineId);
        LocalDate today = LocalDate.now();
        LocalDate firstDate = today.with(ChronoField.DAY_OF_MONTH, 1);
        List<Entry> entries = entryService.monthlyMachineEntry(machine, firstDate, today);
        if (!entries.isEmpty()) {
            CumulativeData data = entries.stream().map(CumulativeData::from)
                    .reduce(new CumulativeData(), (e1, e2) -> {
                        e1.setProduction(e1.getProduction() + (e2.getProduction() != null ? e2.getProduction() : 0.0));
                        e1.setRejection(e1.getRejection() + (e2.getRejection() != null ? e2.getRejection() : 0.0));
                        e1.setOeePercentage(e1.getOeePercentage() + e2.getOeePercentage());
                        e1.setUtilizationPercentage(e1.getUtilizationPercentage() + e2.getUtilizationPercentage());
                        e1.setCount(e1.getCount() + 1);
                        if (!e2.getOeePercentage().equals(0.0)) {
                            e1.setOeeCount(e1.getOeeCount() + 1);
                        }
                        return e1;
                    });

            data.setOeePercentage(Math.round((data.getOeePercentage() * 100.0) / data.getOeeCount()) / 100.0);
            data.setUtilizationPercentage(Math.round((data.getUtilizationPercentage() * 100) / data.getCount()) / 100.0);

            return data;
        } else return CumulativeData.builder()
                .rejection(0.0)
                .production(0.0)
                .oeePercentage(0.0)
                .utilizationPercentage(0.0)
                .build();
    }
}
