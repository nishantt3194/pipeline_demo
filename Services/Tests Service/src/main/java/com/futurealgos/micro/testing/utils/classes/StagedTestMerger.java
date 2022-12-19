/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.classes;

import com.futurealgos.micro.testing.models.base.StagedTest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class StagedTestMerger {

    public final String[] FIELDS_TO_IGNORE;

    public StagedTestMerger() {
        FIELDS_TO_IGNORE = new String[]{
                "objectQueues",
                "attachmentsCompleted",
                "createdOn",
                "modifiedOn",
                "createdBy"
        };
    }

    public StagedTest mergeObjects(StagedTest oldTest, StagedTest newTest) {
        BeanUtils.copyProperties(newTest, oldTest, FIELDS_TO_IGNORE);

        newTest.getQueuedObject().forEach(oldTest.getQueuedObject()::add);

        oldTest.setModifiedOn(new Date());
        return oldTest;
    }
}