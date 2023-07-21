package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.machines.MachineEdit;
import com.futurealgos.admin.dto.payload.machines.NewMachine;
import com.futurealgos.admin.dto.response.machine.*;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.repos.secondary.HolidaysRepository;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.entry.EntryService;
import com.futurealgos.admin.services.holiday.HolidayService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.data.utils.mappings.BaseMap;
import com.futurealgos.specs.objects.DefaultSearchResponse;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/machines/")
public class MachineResource {

    @Autowired
    MachineService machineService;

    @Autowired
    EntryService entryService;

    @Autowired
    AreaService areaService;

    @Autowired
    UserService userService;

    @Autowired
    HolidayService holidayService;


    @GetMapping("create")
    public String create(@RequestBody NewMachine newMachine) {

        machineService.create(newMachine, "");
        return "Machine Created Successfully";

    }

    @GetMapping(value = "info")
    public MachineInfo info(@RequestParam String id) {
        return machineService.info(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<MachineMinimal> list(@RequestParam(required = false, defaultValue = "-1") Integer offset,
                                     @RequestParam(required = false, defaultValue = "-1") Integer size,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false) String userId,
                                     @AuthenticationPrincipal User user) {
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (status != null)
            filters.add(new SearchFilter<>(BaseMap.STATUS, status, SearchOperator.IS));
        if (userId!=null) {
            User operator =userService.fetch(userId);
            filters.add(new SearchFilter<>("operators", operator, SearchOperator.IS));
        }

        return machineService.list(offset, size, new String[0], filters.toArray(SearchFilter[]::new));
    }

    @GetMapping("info/pareto")
    public ParetoData getParetoDataForMachine(@RequestParam("start") String start,
                                              @RequestParam("end") String end,
                                              @RequestParam(value = "id") String machineId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate sDate;
        LocalDate eDate;
        sDate = LocalDate.parse(start, df).minusDays(1);
        eDate = LocalDate.parse(end, df).plusDays(1);
        Machine machine = machineService.fetch(machineId);
        return entryService.getParetoData(machine, sDate, eDate);
    }

    @GetMapping("info/bos")
    public BOSData getBOSDataForMachine(@RequestParam("start") String start,
                                        @RequestParam("end") String end,
                                        @RequestParam(value = "id") String machineId) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate sDate;
        LocalDate eDate;
        sDate = LocalDate.parse(start, df).minusDays(1);
        eDate = LocalDate.parse(end, df).plusDays(1);

        Machine machine = machineService.fetch(machineId);
        return entryService.getBOSData(machine, sDate, eDate);
    }

    @GetMapping("search")
    public List<MachineSearch> search(@RequestParam(name = "area", required = false) String areaId,
                                      @RequestParam(name = "operator",required = false ) String userId ){
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (areaId != null) {
            Area area = areaService.fetch(UUID.fromString(areaId));
            filters.add(SearchFilter.is("area", area));
            filters.add(SearchFilter.is("status",true));
        }
        if (userId!=null){
            return userService.userMachineSearch(userId);
        }
        return machineService.search(filters.toArray(SearchFilter[]::new));
    }

    @GetMapping("stations/search")
    public List<DefaultSearchResponse> searchStations(@RequestParam("machine") String machineId) {
        Machine machine = machineService.fetch(machineId);

        return machine.getStations().stream().map(Station::search).toList();
    }

    @GetMapping("utilization")
    public Double utilizationLoss(
            @RequestParam("id") String machineId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return machineService.utilizationPercetage(machineId, startDate, endDate);
    }

    @GetMapping("info/oee/bos")
    public OeeBOSData getOeeBOSDataForMachine(@RequestParam("start") String start,
                                              @RequestParam("end") String end,
                                              @RequestParam(value = "id") String machineId) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate sDate = LocalDate.parse(start, df);
        LocalDate eDate = LocalDate.parse(end, df);

        Machine machine = machineService.fetch(machineId);
        return entryService.oeeBOSData(machine, sDate, eDate);


    }

    @GetMapping("info/oee/pareto")
    public OeePareto getOeeParetoDataForMachine(@RequestParam("start") String start,
                                              @RequestParam("end") String end,
                                              @RequestParam(value = "id") String machineId) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate sDate = LocalDate.parse(start, df);
        LocalDate eDate = LocalDate.parse(end, df);

        Machine machine = machineService.fetch(machineId);
        return entryService.getOeeParetoData(machine, sDate, eDate);


    }

    @PostMapping("update")
    public MachineInfo editMachine(@RequestBody MachineEdit payload,
                              @AuthenticationPrincipal User user
    )    {
       return machineService.update(payload.id(),payload,user.getId().toString());
    }



}
