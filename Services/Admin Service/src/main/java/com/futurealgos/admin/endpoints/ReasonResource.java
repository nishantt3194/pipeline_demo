package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.NewReason;
import com.futurealgos.admin.dto.payload.shifts.UpdateReason;
import com.futurealgos.admin.dto.response.downtime.DowntimeMinimal;
import com.futurealgos.admin.dto.response.downtime.NewStationSpecificReason;
import com.futurealgos.admin.dto.response.downtime.ReasonMinimal;
import com.futurealgos.admin.dto.response.downtime.ReasonSearch;
import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.entry.DowntimeService;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.reasons.CategoryService;
import com.futurealgos.admin.services.reasons.ReasonService;
import com.futurealgos.admin.services.reasons.StationSpecificReasonService;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.admin.utils.mappings.ReasonMap;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/downtime/")
public class ReasonResource {
    @Autowired
    DowntimeService downtimeService;
    @Autowired
    MachineService machineService;
    @Autowired
    StationSpecificReasonService stationSpecificReasonService;
    @Autowired
    ReasonService reasonService;
    @Autowired
    CategoryService categoryService;


    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody NewReason reason, @AuthenticationPrincipal User user){
        reasonService.create(reason, user.getId().toString());

        return ResponseEntity.accepted().body("Reason Added Successfully");
    }

    @GetMapping("list")
    public Page<DowntimeMinimal> list(@RequestParam int offset,
                                      @RequestParam int size,
                                      @RequestParam String entryId) {

        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(new SearchFilter<>("entry", entryId, SearchOperator.IS));

        return downtimeService.list(offset, size, new String[0], filters.toArray(SearchFilter[]::new));
    }

    @GetMapping("search")
    public List<ReasonSearch> search(@RequestParam(value = "machine", required = false) String machineId,
    @RequestParam(value = "category", required = false) String categoryId) {
        if (machineId != null) {
            Machine machine = machineService.fetch(machineId);
            return stationSpecificReasonService.search(SearchFilter.is("machine", machine));

        } else if(categoryId != null) {
            Category category = categoryService.fetch(categoryId);
            return reasonService.search(SearchFilter.is("defaultCategory", category));

        }
        else {
            return reasonService.search(SearchFilter.is(ReasonMap.TYPE,BreakdownType.COMMON));
        }

    }

    @PostMapping("map")
    public ResponseEntity<String> mapStationReason(@RequestBody NewStationSpecificReason payload, @AuthenticationPrincipal User user)
    {
        stationSpecificReasonService.create(payload, user.getId().toString());
        return ResponseEntity.accepted().body("Reason Mapped Successfully");
    }
    @GetMapping ("reason/list")
    public Page<ReasonMinimal > reasonMinimalList(@RequestParam int size,
                                                  @RequestParam int offset,
                                                  @RequestParam String category){

        return reasonService.list(size, offset , new String[0], SearchFilter.is("defaultCategory",category));

    }

    @PutMapping("reason/status")
    public String updateStatus(@RequestBody List<UpdateReason> payloads){

         reasonService.updateStatus(payloads);
         return  "Status Updated Successfully";
    }
}
