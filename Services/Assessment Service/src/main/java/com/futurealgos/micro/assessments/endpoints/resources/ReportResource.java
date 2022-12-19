/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;


import com.futurealgos.micro.assessments.dto.payload.NormCartesianWrapper;
import com.futurealgos.micro.assessments.dto.payload.ReportPayload;
import com.futurealgos.micro.assessments.dto.response.ReportInfo;
import com.futurealgos.micro.assessments.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportResource {

    @Autowired
    ReportService reportService;

    @GetMapping("/create")
    public ReportInfo testApi(@RequestParam ReportPayload payload){

       return reportService.generateReport(payload);

    }

}
