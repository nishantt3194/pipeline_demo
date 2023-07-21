package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.precheck.NewPrecheck;
import com.futurealgos.admin.dto.response.precheck.PrecheckInfo;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.machines.PrecheckService;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/precheck/")
public class PrecheckResource {

    @Autowired
    PrecheckService precheckService;

    @Autowired
    MachineService machineService;


    @GetMapping("search")
    public List<PrecheckInfo> search(@RequestParam String machineId) {
        Machine machine = machineService.fetch(machineId);
        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(SearchFilter.is("machine", machine));
        return precheckService.search(filters.toArray(SearchFilter[]::new));
    }


    @PostMapping("add")
    public PrecheckInfo create(@RequestBody NewPrecheck precheck) {
        return precheckService.create(precheck, "");
    }

    @PutMapping("edit")
    public PrecheckInfo edit(@RequestBody NewPrecheck precheck,
                             @AuthenticationPrincipal User user){
        return precheckService.update(precheck.id(),precheck,"");
    }

    @DeleteMapping()
    public String remove(@RequestParam("id") String id){
        precheckService.deletePrecheck(id);
        return  "PreCheck Removed Successfully ";
    }

}
