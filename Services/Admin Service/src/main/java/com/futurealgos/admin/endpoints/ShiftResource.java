package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.shifts.EditShift;
import com.futurealgos.admin.dto.payload.shifts.NewShift;
import com.futurealgos.admin.dto.response.shifts.ShiftInfo;
import com.futurealgos.admin.dto.response.shifts.ShiftMinimal;
import com.futurealgos.admin.dto.response.shifts.ShiftSearch;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.admin.services.administration.ShiftService;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shift/")
public class ShiftResource {

    @Autowired
    ShiftService shiftService;
    @Autowired
    AreaService areaService;

    @PostMapping("create")
    public String newShift(@RequestBody NewShift newShift) {
        shiftService.create(newShift, "");
        return null;
    }

    @GetMapping("info")
    public ShiftInfo shiftInfo(@RequestParam String id) {
        return shiftService.info(id);
    }

    @GetMapping(value = "list")
    public Page<ShiftMinimal> list(@RequestParam String offset, String size) {
        return shiftService.list(-1, -1);
    }

    @GetMapping("search")
    public List<ShiftSearch> search(@RequestParam(required = false, name = "area") String areaId) {
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (areaId != null) {
            Area area = areaService.fetch(UUID.fromString(areaId));
            filters.add(new SearchFilter<>("area", area, SearchOperator.IS));
        }

        return shiftService.search(filters.toArray(SearchFilter[]::new));
    }


    @GetMapping(value = "machine/shifts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShiftSearch> fetchShiftNames(@RequestParam(value = "machine") String machine,
                                        @RequestParam(value = "date") String date,
                                             @RequestParam (value = "area") String  area){
        return shiftService.getShiftNames(machine, date , area);
    }

    @PutMapping(value = "edit", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public String editShift(@RequestBody EditShift payload){
        shiftService.update(payload.id(),payload,"admin");
        return "Shift Edited Successfully";
    }

    public String removeShift(@RequestParam (value = "id") String id){
        shiftService.removeShift(id);
        return "Shift deleted successfully";
    }
}
