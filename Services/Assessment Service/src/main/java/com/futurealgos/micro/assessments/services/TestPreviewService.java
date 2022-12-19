package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.response.exec.TestPreview;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.repos.TestRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestPreviewService {

    @Autowired
    TestRepository testRepository;
    public Test fetch(String identifier) {
        return testRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Tests with ID " + identifier));
    }

    public TestPreview fetchPreview(String identifier) {
        return TestPreview.build(fetch(identifier));
    }

}
