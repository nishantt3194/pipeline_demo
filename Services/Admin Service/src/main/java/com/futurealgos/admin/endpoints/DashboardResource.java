package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.entry.MachineEntryInfo;
import com.futurealgos.admin.dto.response.entry.CumulativeData;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.exception.exceptions.UnauthorizedException;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.services.entry.EntryService;
import com.futurealgos.admin.services.entry.StagedEntryService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.specs.utils.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dashboard/")
public class DashboardResource {

    @Autowired
    MachineService machineService;

    @Autowired
    UserService userService;
    @Autowired
    EntryService entryService;
    @Autowired
    StagedEntryService stagedEntryService;

    @GetMapping("top/machine")
    public MachineEntryInfo topPerformingMachine(@AuthenticationPrincipal User user,
                                                 @RequestParam (value = "area" , required = false )String id)  {
        if (user.getRole().equals(Role.OPERATOR)) {
            return machineService.topPerformingMachine(user);
        }
        if (id!=null)
        {
            return entryService.areaTopPerformingMachine(id);
        }
        return null;
    }

    @GetMapping("operator/last/five/entry")
    public List<EntryMinimal> last5Entries(@AuthenticationPrincipal User user) {
        return entryService.lastFiveEntries(user);
    }

    @GetMapping("operator/staged/entry")
    public List<EntryMinimal> stagedEntry(@AuthenticationPrincipal User user
    ) {
        if(user.getRole().equals(Role.OPERATOR))
            return stagedEntryService.search(SearchFilter.is("operator", user));
        else
            throw new UnauthorizedException("Unauthorised Access");

    }

    @GetMapping("super/top/machines")
    public List<MachineEntryInfo> topPerformingMachines(
            @AuthenticationPrincipal User user
    ) {
        if (user.getRole().equals(Role.ADMINISTRATOR) || user.getRole().equals(Role.SUPERVISOR)) {
            return entryService.topPerformingMachines();
        }
        return  null;
    }

    @GetMapping("super/three/entry")
    public List<EntryMinimal> last3entry(@AuthenticationPrincipal User user){
        return  entryService.last3Entries();
    }


    @GetMapping("super/total/data")
    public CumulativeData totalProdAndRej(@RequestParam String machineId){
        return machineService.cummulativeData(machineId);
    }

}
