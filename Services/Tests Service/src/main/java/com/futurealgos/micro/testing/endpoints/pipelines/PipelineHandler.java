/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.pipelines;

import com.futurealgos.micro.testing.models.base.StagedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineHandler {

    @Autowired
    PipelineGateway gateway;

    public void ingest(Boolean submitted, String administrator, StagedTest payload) {
        gateway.ingest(submitted, administrator, payload);
    }

    public void tests() {
        StagedTest stagedTest = new StagedTest();
        stagedTest.setId("TEST1234");
        gateway.tests(stagedTest);
    }
}
