/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;

import com.futurealgos.micro.assessments.repos.AnswerSheetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Package: com.futurealgos.micro.assessments.endpoints.resources
 * Project: Prasad Psycho
 * Created On: 28/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@RestController("/")
public class HealthCheck {

    @Autowired
    AssessmentResource assessmentResource;

    @Autowired
    AnswerSheetRepo answerSheetRepo;

    @GetMapping("health")
    public String getHealth() {

//        NewAssessment trt = new NewAssessment(
//                LinkType.GROUP,
//                Type.UNSCHEDULED,
//                null,
//                false,
//                null,
//                null,
//                "62ea4afb471fbb0db1656821",
//                "62e1a52ca15be321bb72d6fe",
//                new ArrayList<>()
//        );
//
//        assessmentResource.create(trt);

        return "OK";
    }
}
