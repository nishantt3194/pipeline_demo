/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;

import com.futurealgos.micro.assessments.dto.payload.NewExaminee;
import com.futurealgos.micro.assessments.dto.response.admin.ARMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeInfo;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeSearch;
import com.futurealgos.micro.assessments.services.ExamineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/examinee/")
public class ExamineeResource {

    @Autowired
    ExamineeService examineeService;


    /**
     * TODO:
     * @param payload
     * @return
     */
    @PostMapping("create")
    public ResponseEntity<String> create (@RequestBody NewExaminee payload){
        examineeService.create(payload,"");
        return ResponseEntity.accepted().body("Examinee Added Successfully");
    }

    /**
     *
     * @param identifier
     * @return
     */
    @GetMapping("info")
    public ExamineeInfo info(@RequestParam("id") String identifier){
        return examineeService.info(identifier);
    }

    /**
     *
     * @return
     */
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ExamineeSearch>> searchAll(@RequestParam("partner") String partner) {
        return ResponseEntity.ok(examineeService.search(partner));
    }

    @GetMapping(value = "list",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ExamineeMinimal>> list(@RequestParam("partner") String partner,
                                                      @RequestParam(value = "offset", required = false) Integer offset,
                                                      @RequestParam(value = "size", required = false) Integer size){
//        if (paged)
//            return ResponseEntity.ok(examineeService.list(PageRequest.of(offset, size)));

        return  ResponseEntity.ok(examineeService.list(partner, Pageable.unpaged()));
    }


    @GetMapping(value = "assessment/list")
    public ResponseEntity<List<ARMinimal>>listAssessment(@RequestParam String id){
        return  ResponseEntity.ok(examineeService.listOfAssessments(id));
    }

}
