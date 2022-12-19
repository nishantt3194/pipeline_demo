/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.resources;


import com.futurealgos.micro.testing.dto.payload.category.NewCategory;
import com.futurealgos.micro.testing.dto.response.category.CategoryMinimal;
import com.futurealgos.micro.testing.dto.response.category.CategorySearch;
import com.futurealgos.micro.testing.exceptions.BadRequestException;
import com.futurealgos.micro.testing.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories/")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("create")
    public ResponseEntity<String> createTestCategory(@RequestBody @Valid NewCategory payload,
                                                     BindingResult result) {

        if (result.hasErrors())
            throw new BadRequestException("Invalid Data Format. Please check values again before submitting");


        categoryService.create(payload, "admin");

        return ResponseEntity.accepted().body("Category Added Successfully");
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CategoryMinimal>> fetchList(
            @RequestParam(value = "paged") Boolean paged,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer size) {
        if (paged)
            return ResponseEntity.ok(categoryService
                    .list(PageRequest.of(offset, size)));

        else return ResponseEntity.ok(categoryService
                .list(Pageable.unpaged()));
    }


    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategorySearch>> searchAll() {
        return ResponseEntity.ok(categoryService.search());
    }
}
