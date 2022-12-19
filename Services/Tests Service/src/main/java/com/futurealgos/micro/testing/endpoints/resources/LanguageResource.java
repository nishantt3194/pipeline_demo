/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.resources;

import com.futurealgos.micro.testing.dto.payload.language.NewLanguage;
import com.futurealgos.micro.testing.dto.response.language.LanguageMinimal;
import com.futurealgos.micro.testing.dto.response.language.LanguageSearch;
import com.futurealgos.micro.testing.exceptions.BadRequestException;
import com.futurealgos.micro.testing.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/languages/")
public class LanguageResource {

    @Autowired
    LanguageService languageService;

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody @Valid NewLanguage payload,
                                         BindingResult result) {
        if (result.hasErrors())
            throw new BadRequestException("Invalid Data Format. Please check values again before submitting");

        languageService.create(payload, "admin");

        return ResponseEntity.accepted().body("Language Registered Successfully");
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<LanguageMinimal>> fetchList(@RequestParam("offset") int offset,
                                                           @RequestParam("size") int size) {
        return ResponseEntity.ok(languageService.list(PageRequest.of(offset, size)));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LanguageSearch>> searchAll() {
        return ResponseEntity.ok(languageService.search());
    }
}
