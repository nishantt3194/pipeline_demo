package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.response.downtime.CategoryInfo;
import com.futurealgos.admin.services.reasons.CategoryService;
import com.futurealgos.data.utils.mappings.BaseMap;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category/")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @GetMapping("search")
    public List<CategoryInfo> search() {
        List<SearchFilter<?>> filters = new ArrayList<>();
        filters.add(SearchFilter.isNull("parentCategory"));
        return categoryService.search(filters.toArray((SearchFilter[]::new)));
    }

}
