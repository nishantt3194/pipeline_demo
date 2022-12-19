/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services.tests;

import com.futurealgos.micro.testing.dto.payload.tests.NewInterpretation;
import com.futurealgos.micro.testing.dto.response.tests.InterpretationSearchResponse;
import com.futurealgos.micro.testing.dto.response.tests.TestInfo;
import com.futurealgos.micro.testing.dto.response.tests.TestsMinimal;
import com.futurealgos.micro.testing.exceptions.AlreadyExistsException;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.DetailedInterpretation;
import com.futurealgos.micro.testing.models.base.Partner;
import com.futurealgos.micro.testing.models.base.QualitativeDescriptor;
import com.futurealgos.micro.testing.models.base.Test;
import com.futurealgos.micro.testing.repos.DescriptiveInterpretationRepository;
import com.futurealgos.micro.testing.repos.DetailedInterpretationRepository;
import com.futurealgos.micro.testing.repos.TestRepository;
import com.futurealgos.micro.testing.utils.enums.InterpretationType;
import com.futurealgos.micro.testing.utils.enums.TestStatus;
import com.futurealgos.micro.testing.utils.specs.services.ImmutableSpec;
import com.futurealgos.micro.testing.utils.specs.services.InternalUpdateSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestDataService implements ImmutableSpec<Test, TestInfo, TestsMinimal>, InternalUpdateSpec<Test> {

    TestRepository testRepository;
    DetailedInterpretationRepository detailedInterpretationRepository;
    DescriptiveInterpretationRepository descriptiveInterpretationRepository;
    StagingService stagingService;

    @Autowired
    public TestDataService(TestRepository testRepository,
                           DetailedInterpretationRepository detailedInterpretationRepository,
                           DescriptiveInterpretationRepository descriptiveInterpretationRepository,
                           StagingService stagingService) {
        Assert.notNull(testRepository, "TestRepository cannot be null");
        Assert.notNull(detailedInterpretationRepository, "DetailedInterpretationRepository cannot be null");
        Assert.notNull(descriptiveInterpretationRepository, "DescriptiveInterpretationRepository cannot be null");
        Assert.notNull(stagingService, "StagingService cannot be null");

        this.testRepository = testRepository;
        this.detailedInterpretationRepository = detailedInterpretationRepository;
        this.descriptiveInterpretationRepository = descriptiveInterpretationRepository;
        this.stagingService = stagingService;
    }

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


    @Override
    @Transactional(readOnly = true)
    public TestInfo info(String identifier) {
        Test test = fetch(identifier);
        return TestInfo.populate(test);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestsMinimal> list(Test test, Pageable pageable) {
//          return testRepository.list(Test.class,TestsMinimal.FIELDS, map, pageable)
//                  .map(TestsMinimal::build);
        return null;

    }

    @Override
    public Page<TestsMinimal> list(Pageable pageable) {
        return testRepository.list(Test.class, TestsMinimal.FIELDS, pageable)
                .map(TestsMinimal::build);
    }

    @Override
    public Test update(Test payload, String admin) {
        return testRepository.save(payload, admin);
    }

    @Override
    public Long count() {
        return testRepository.count();
    }

    public Page<TestsMinimal> list(Pageable pageable, String status) {
        TestStatus testStatus = TestStatus.valueOf(status);
        return testRepository.findByStatus(testStatus, pageable)
                .map(TestsMinimal::build);
//        return testRepository.list(Test.class, TestsMinimal.FIELDS, pageable)
//                .map(TestsMinimal::build);
    }

    public Page<TestsMinimal> marketplace(Pageable pageable, Partner partner) {
        if (partner.isAclStatus()) {
            if (partner.getAclType().equals(Partner.ACLType.WHITELISTED)) {
                List<TestsMinimal> data = partner.getAclTests().stream().map(TestsMinimal::build).toList();
                return PageableExecutionUtils
                        .getPage(data, pageable, data::size);
            } else {
                return testRepository.findByStatusAndIdNotIn(TestStatus.ACTIVE, pageable,
                                partner.getAclTests().stream().map(Test::getId).toList())
                        .map(TestsMinimal::build);
            }
        }

        return testRepository.findByStatus(TestStatus.ACTIVE, pageable)
                .map(TestsMinimal::build);
    }

    /**
     * Finalizes a Test by setting the status to ACTIVE.
     *
     * @param identifier ID of Test which is required to be finalized
     * @param admin      ID of the Administrator finalizing the Test
     * @throws AlreadyExistsException if the Test is already finalized
     * @throws NotFoundException      if the Test is not found
     */
    public void finalizeTest(String identifier, String admin) throws AlreadyExistsException {
        Test test = fetch(identifier);
        if (test.getStatus().equals(TestStatus.ACTIVE))
            throw new AlreadyExistsException("Test is already active");
        test.setStatus(TestStatus.ACTIVE);
        testRepository.save(test, admin);

    }

    /**
     * Checks if Test ID is available or not
     *
     * @param id ID of Test which is required to be checked
     * @return true if Test is available, false otherwise
     */
    public boolean checkIDAvailability(String id) {
        Test test = testRepository.findByTestId(id).orElse(null);

        if (test != null) return false;

        try {
            stagingService.fetch(id);
            return false;
        } catch (NotFoundException e) {
            return true;
        }
    }


    public TestInfo createInterpretation(List<NewInterpretation> payload, String admin) {
        Test test = fetch(payload.get(0).testId());
        for (NewInterpretation newInterpretation : payload) {
            if (newInterpretation.type().equals(InterpretationType.DETAILED)) {
                DetailedInterpretation interpretation = DetailedInterpretation.
                        builder()
                        .test(test)
                        .details(newInterpretation.description())
                        .interpretationName(newInterpretation.name())
                        .build();

                interpretation = detailedInterpretationRepository.save(interpretation, admin);
                if (test.getDetailedInterpretations() != null) {
                    test.getDetailedInterpretations().add(interpretation);
                } else {
                    List<DetailedInterpretation> interpretations = new ArrayList<>();
                    interpretations.add(interpretation);
                    test.setDetailedInterpretations(interpretations);
                }

            } else {
                QualitativeDescriptor interpretation = QualitativeDescriptor.
                        builder()
                        .test(test)
                        .description(newInterpretation.name())
                        .build();

                interpretation = descriptiveInterpretationRepository.save(interpretation, admin);
                if (test.getDetailedInterpretations() != null) {
                    test.getQualitativeDescriptors().add(interpretation);
                } else {
                    List<QualitativeDescriptor> interpretations = new ArrayList<>();
                    interpretations.add(interpretation);
                    test.setQualitativeDescriptors(interpretations);
                }
            }
        }


        return TestInfo.populate(testRepository.save(test, admin));
    }

    public List<InterpretationSearchResponse> searchInterpretations(String testId, InterpretationType type) {
        if (type.equals(InterpretationType.QUALITATIVE))
            return descriptiveInterpretationRepository
                    .findByTest_Id(new ObjectId(testId)).stream()
                    .map(intr -> new InterpretationSearchResponse(intr.getDescription(), intr.getId().toHexString()))
                    .toList();
        return detailedInterpretationRepository.findByTest_Id(new ObjectId(testId)).stream().
                map(intr -> new InterpretationSearchResponse(intr.getInterpretationName(), intr.getId().toHexString()))
                .toList();
    }
}
