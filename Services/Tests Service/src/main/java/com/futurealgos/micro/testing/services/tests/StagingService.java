/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services.tests;

import com.futurealgos.micro.testing.dto.payload.AttachmentsPayload;
import com.futurealgos.micro.testing.dto.response.tests.SavedTestsMinimal;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.embedded.QueuedObject;
import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.repos.StagedTestRepo;
import com.futurealgos.micro.testing.utils.classes.StagedTestMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class StagingService {

    @Autowired
    public MongoTemplate template;

    @Autowired
    StagedTestRepo stagedTestRepo;

    @Autowired
    StagedTestMerger stagedTestMerger;

    public StagedTest fetch(String id) {
        return stagedTestRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Could not find Saved Test with ID " + id));
    }

    public AttachmentsPayload attach(AttachmentsPayload payload) {
        StagedTest test = stagedTestRepo.findById(payload.id).orElse(null);

        if (test != null) {
            QueuedObject queuedObject = test.getQueuedObject().stream()
                    .filter(oq -> oq.getTag().equals(payload.tag))
                    .findFirst().orElse(null);


            if (queuedObject != null) {
                test.getQueuedObject().remove(queuedObject);
                queuedObject.setId(payload.metadata);
                queuedObject.setStatus("attached");
                queuedObject.setAlias(payload.fileName);
                test.getQueuedObject().add(queuedObject);
                payload.attached = true;
            }

            persist(test);
            return payload;
        }

        payload.attached = false;
        return payload;
    }


    public StagedTest merge(StagedTest payload) {
        StagedTest test = stagedTestRepo.findById(payload.getId()).orElse(null);
        if (test == null) {
            payload.setCreatedBy(payload.getLastModifiedBy());
            payload.setCreatedOn(new Date());
            return payload;
        }
        return stagedTestMerger.mergeObjects(test, payload);
    }

    public StagedTest persist(StagedTest payload) {
        return stagedTestRepo.save(payload);
    }

    public Page<SavedTestsMinimal> list(Pageable pageable) {
        return fetchList(pageable).map(SavedTestsMinimal::build);
    }

    private Page<StagedTest> fetchList(Pageable pageable) {
        String[] fields = new String[]{};

        Query query;

        if (pageable.isUnpaged())
            query = new Query()
                    .allowDiskUse(true);

        else {
            query = new Query().with(pageable)
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .allowDiskUse(true);
        }

        query.fields().include(fields);

        List<StagedTest> data = template.find(query, StagedTest.class);

        if (!pageable.isUnpaged()) {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            () -> template.count(query.skip(-1).limit(-1), StagedTest.class));
        } else {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            data::size);
        }

    }

    public int discard(String[] ids) {
        int counter = 0;
        for (int i = 0; i < ids.length; i++) {
            String identifier = ids[i];
            StagedTest test = stagedTestRepo.findById(identifier)
                    .orElse(null);

            if (test != null) {
                stagedTestRepo.delete(test);
                counter++;
            }
        }

        return counter;
    }

    public void delete(String id) {
        stagedTestRepo.deleteById(id);
    }
}
