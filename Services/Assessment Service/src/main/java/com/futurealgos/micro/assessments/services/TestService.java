/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.response.admin.TestSearch;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.repos.TestRepository;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TestService implements FetcherSpec<Test> {

    @Autowired
    TestRepository testRepository;

    @Override
    public Test fetch(String identifier) {
        return testRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Tests with ID " + identifier));
    }

    @Override
    public Test fetch(ObjectId identifier) {
        return testRepository.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find Tests with ID " + identifier));
    }

    @Override
    public List<Test> fetchAll(List<String> identifier) {
        List<Test> result = new ArrayList<>();
        testRepository.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    public List<TestSearch> search(){
        return testRepository.findAll()
                .stream()
                .map(TestSearch::new).toList();
    }


}
