/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.NewAssessment;
import com.futurealgos.micro.assessments.dto.response.admin.ARInfo;
import com.futurealgos.micro.assessments.dto.response.admin.ARMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.AssessmentSearch;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.repos.ARRepo;
import com.futurealgos.micro.assessments.utils.specs.services.InternalUpdateSpec;
import com.futurealgos.micro.assessments.utils.specs.services.ServiceSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class AssessmentService implements ServiceSpec<AssessmentRequest, NewAssessment, ARInfo, ARMinimal>,
        InternalUpdateSpec<AssessmentRequest> {

    @Autowired
    ARRepo assessmentRequestRepo;
    @Autowired
    TestService testService;

//    @Autowired
//    AssigneeService assigneeService;

    @Autowired
    ExamineeService examineeService;

    @Override
    public AssessmentRequest fetch(String identifier) throws NotFoundException {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public AssessmentRequest fetch(ObjectId identifier) throws NotFoundException {
        return assessmentRequestRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find any Assessment Request with ID " + identifier));
    }

    @Override
    public List<AssessmentRequest> fetchAll(List<String> identifier) {
        List<AssessmentRequest> result = new ArrayList<>();
        assessmentRequestRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    @Override
    public AssessmentRequest create(NewAssessment payload, String admin) throws ParseException {
        AssessmentRequest request = AssessmentRequest.build(payload);
        Test test = testService.fetch(payload.test());
        request.setTest(test);
        request.setNorms(test.getNorms());
        request.buildRequestId();

        request = assessmentRequestRepo.save(request, admin);

        return request;
    }


    @Override
    public ARInfo info(String identifier) {
        return ARInfo.build(fetch(identifier));
    }

    @Override
    public Page<ARMinimal> list(AssessmentRequest entity, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ARMinimal> list(Pageable pageable) {
        return assessmentRequestRepo.findAll(pageable).map(ARMinimal::convert);
    }


    @Override
    public Long count() {
        return null;
    }


    @Override
    public AssessmentRequest update(AssessmentRequest payload, String admin) {
        return assessmentRequestRepo.save(payload, admin);
    }

    public List<AssessmentSearch> search(){
        return assessmentRequestRepo.findAll()
                .stream()
                .map(AssessmentSearch::new).toList();
    }

}
