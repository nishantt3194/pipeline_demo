/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services.tests;

import com.futurealgos.micro.testing.dto.payload.SubmitProcessingWrapper;
import com.futurealgos.micro.testing.dto.payload.tests.NewQuestion;
import com.futurealgos.micro.testing.dto.payload.tests.NewSubscale;
import com.futurealgos.micro.testing.models.base.*;
import com.futurealgos.micro.testing.models.embedded.QueuedObject;
import com.futurealgos.micro.testing.repos.SubscaleRepository;
import com.futurealgos.micro.testing.services.CategoryService;
import com.futurealgos.micro.testing.services.LanguageService;
import com.futurealgos.micro.testing.services.MetadataService;
import com.futurealgos.micro.testing.services.NormsService;
import com.futurealgos.micro.testing.utils.enums.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Package: com.futurealgos.micro.testing.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class SubmissionService {

    CategoryService categoryService;
    NormsService normsService;
    LanguageService languageService;
    MetadataService metadataService;

    SubscaleRepository subscaleRepository;

    TestDataService testDataService;

    TestDataProcessor testDataProcessor;

    @Autowired
    public SubmissionService(CategoryService categoryService,
                             NormsService normsService,
                             LanguageService languageService,
                             MetadataService metadataService,
                             SubscaleRepository subscaleRepository,
                             TestDataService testDataService,
                             TestDataProcessor testDataProcessor) {
        this.categoryService = categoryService;
        this.normsService = normsService;
        this.languageService = languageService;
        this.metadataService = metadataService;
        this.subscaleRepository = subscaleRepository;
        this.testDataService = testDataService;
        this.testDataProcessor = testDataProcessor;
    }

    /**
     * Process Basic Data for the Test
     * <p>
     *     Evaluates Saved Test to build Basic Textual Data for the Test,
     *     which is then saved in the Database as a Draft.
     * </p>
     *
     * @param payload Payload containing saved information of the test
     * @param admin Administrator performing the action
     * @return Wrapper containing the processed data, payload, and administrator
     */
    public SubmitProcessingWrapper process(StagedTest payload, String admin) {
        TestCategory category = categoryService.fetch(payload.getTestCategory());

        List<Norm> norms = normsService.fetchAll(payload.getNorms());
        DocumentMetadata documentMetadata = new DocumentMetadata();

        LanguageStore languageStore = languageService.fetch(payload.getLanguage());
        if (payload.getLogo() != null) {
            for (QueuedObject queue : payload.getQueuedObject()) {
                if (payload.getLogo().equals(queue.getTag())) {
                    documentMetadata = metadataService.fetch(queue.getId());
                    payload.getQueuedObject().remove(queue);
                    break;
                }
            }
        }

        Test test = payload.shell().norms(norms)
                .testCategory(category)
                .language(languageStore)
                .logo(documentMetadata)
                .build();

        test = testDataService.update(test, admin);

        return SubmitProcessingWrapper.builder()
                .payload(payload)
                .test(test)
                .admin(admin)
                .build();
    }

    /**
     * Process the Wrapper to Build Subscale Data
     * <p>
     *     Evaluates Saved Test to build Subscale Data for the Test,
     *     It passes on the Default Subscale(if mode is Default)
     *     else the user defined subscales are used.
     * </p>
     *
     *<p>
     *     invokes {@link #processQuestions(StagedTest) processQuestions} to build Questions Data from payload
     *     <br/>
     *     invokes {@link #buildSubscales(List, Test, String, Set) buildSubscales} to build subscales from payload
     * </p>
     * @param wrapper Wrapper containing the payload, Test data
     * @return
     */
    public SubmitProcessingWrapper subscaleProcessing(SubmitProcessingWrapper wrapper) {
        processQuestions(wrapper.payload);
        List<NewSubscale> subscales = wrapper.payload.getSubscales();
        if (wrapper.payload.getMode().equals(Mode.DEFAULT))
            subscales = new ArrayList<>(Collections.singletonList(wrapper.payload.getDefaultSubscale()));

        buildSubscales(subscales, wrapper.test, wrapper.admin, wrapper.payload.getQueuedObject());
        return wrapper;
    }


    /**
     * Process the Payload to build Questions data
     * @param payload Payload containing the data of the test to be built
     */
    public void processQuestions(StagedTest payload) {
        if (payload.getMode().equals(Mode.DEFAULT)) {
            payload.getDefaultSubscale().questions().addAll(payload.getQuestions());
        } else {
            List<NewSubscale> subscales = payload.getSubscales();
            for (NewQuestion qs : payload.getQuestions()) {
                String subscaleName = qs.subscale();
                subscales.stream().filter(sub -> sub.name().equals(subscaleName))
                        .findFirst().ifPresent(subscale -> subscale.questions().add(qs));
            }
            payload.setSubscales(subscales);
        }
    }

    /**
     * Build Subscales and respective Questions from the Payload
     * @param subscales List contains payload required to build Subscales
     * @param test Tests in which built subscales will be added
     * @param admin Administrator performing the action
     * @param queues Queue containing the uploaded files that will be mapped with corresponding questions
     */
    private void buildSubscales(List<NewSubscale> subscales, Test test, String admin, Set<QueuedObject> queues) {
        for (NewSubscale payload : subscales) {
            Subscale subscale = payload.shell().test(test).build();
            subscale = subscaleRepository.save(subscale, admin);
            test.getSubscales().add(subscale);

            for (NewQuestion qsPayload : payload.questions()) {
                test.getQuestions().add(testDataProcessor.buildQuestion(qsPayload, queues, subscale, admin));
            }
        }
    }


    /**
     * Evaluates and generates Score Tables for the test based on Norms and Subscales
     * invokes {@link TestDataProcessor#buildScoreTables(Test, String)}  buildScoreTables} to generate Score Table data
     * @param wrapper Wrapper containing the payload, Test data, and Administrator
     * @return Processed Wrapper containing the payload, Test data, and Administrator after tables are generated
     */
    public SubmitProcessingWrapper generateTables(SubmitProcessingWrapper wrapper) {
        testDataService.update(testDataProcessor.buildScoreTables(wrapper.test, wrapper.admin), wrapper.admin);
        return wrapper;
    }


    /**
     * Performs the final submit to close off the pipeline
     * @param wrapper Wrapper containing the payload, Test data, and Administrator
     * @return Processed Wrapper containing the payload, Test data, and Administrator after test has been completely submitted
     */
    public SubmitProcessingWrapper submit(SubmitProcessingWrapper wrapper) {
        testDataService.update(wrapper.test, wrapper.admin);
        return wrapper;
    }

}
