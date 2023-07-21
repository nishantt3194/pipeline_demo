package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.area.NewAreaPayload;
import com.futurealgos.admin.dto.response.area.AreaInfo;
import com.futurealgos.admin.dto.response.area.AreaSearch;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.area.AreaService;
import com.futurealgos.data.utils.mappings.BaseMap;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/area/")
public class AreaResource {
    @Autowired
    AreaService areaService;

    @PostMapping("create")
    public String create(@RequestBody NewAreaPayload areaPayload, @AuthenticationPrincipal User user) {
        areaService.create(areaPayload, user.getId().toString());
        return "Area created successfully";
    }

    @GetMapping("info")
    public AreaInfo info(@RequestParam String id) {
        return areaService.info(id);

    }

    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AreaSearch> search() {
        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(new SearchFilter<>(BaseMap.STATUS, true, SearchOperator.IS));
        return areaService.search(filters.toArray(SearchFilter[]::new));
    }

    @GetMapping("list")
    public Page<AreaInfo> list(@RequestParam Integer offset, @RequestParam Integer size) {
        return areaService.list(offset, size);
    }

}
