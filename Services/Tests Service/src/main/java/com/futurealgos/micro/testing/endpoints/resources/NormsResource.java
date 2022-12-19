/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.resources;

import com.futurealgos.micro.testing.dto.payload.norms.NewNorm;
import com.futurealgos.micro.testing.dto.response.norms.NormsMinimal;
import com.futurealgos.micro.testing.dto.response.norms.NormsSearch;
import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.services.NormsService;
import com.futurealgos.micro.testing.utils.classes.PayloadErrorResolver;
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
@RequestMapping("/norms/")
public class NormsResource {

    @Autowired
    private NormsService normsService;

    @Autowired
    PayloadErrorResolver payloadErrorResolver;

    @PostMapping("create")
    public ResponseEntity<String> createNorm(@RequestBody @Valid NewNorm payload, BindingResult result) {
        if (result.hasErrors())
            payloadErrorResolver.resolve(result);

        normsService.create(payload, "");
        return ResponseEntity.accepted().body("Norm Created Successfully");
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<NormsMinimal>> fetchList(
            @RequestParam(value = "paged") Boolean paged,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer size) {
        if (paged)
            return ResponseEntity.ok(normsService
                    .list(PageRequest.of(offset, size)));

        else return ResponseEntity.ok(normsService
                .list(Pageable.unpaged()));
    }

    @GetMapping("view")
    public ResponseEntity<Norm> previewNorm(@RequestParam("id") String normId) {
        return ResponseEntity.ok(normsService.fetch(normId));

    }

    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NormsSearch>> searchAll() {
        return ResponseEntity.ok(normsService.search());
    }

}
