/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.resources;

import com.futurealgos.micro.testing.dto.payload.StatusModificationPayload;
import com.futurealgos.micro.testing.dto.payload.score.NewScore;
import com.futurealgos.micro.testing.dto.payload.tests.EditCredit;
import com.futurealgos.micro.testing.dto.payload.tests.EditTest;
import com.futurealgos.micro.testing.dto.payload.tests.NewInterpretation;
import com.futurealgos.micro.testing.dto.response.WeightedResponse;
import com.futurealgos.micro.testing.dto.response.tests.*;
import com.futurealgos.micro.testing.endpoints.pipelines.PipelineHandler;
import com.futurealgos.micro.testing.exceptions.AlreadyExistsException;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.Partner;
import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.repos.StagedTestRepo;
import com.futurealgos.micro.testing.services.PartnerService;
import com.futurealgos.micro.testing.services.tests.StagingService;
import com.futurealgos.micro.testing.services.tests.TestDataService;
import com.futurealgos.micro.testing.services.tests.TestModificationService;
import com.futurealgos.micro.testing.utils.classes.PayloadErrorResolver;
import com.futurealgos.micro.testing.utils.enums.InterpretationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/")
public class TestResource {

    @Autowired
    TestDataService testService;
    @Autowired
    TestModificationService modificationService;
    @Autowired
    StagingService stagingService;

    @Autowired
    PartnerService partnerService;
    @Autowired
    PayloadErrorResolver payloadErrorResolver;

    @Autowired
    PipelineHandler handler;

    @Autowired
    StagedTestRepo repo;


    /**
     * Triggers Ingestion Pipeline for Test Creation Flow
     *
     * @param payload   Test Payload to be ingested, could be either new or existing
     * @param submitted True if the test is being submitted, false if it is being staged
     * @param result    BindingResult of the payload, check for any validation errors
     *                  and return them in the response.
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void stageTest(@RequestBody StagedTest payload,
                          @RequestParam(value = "submitted") Boolean submitted,
                          BindingResult result) {
        if (result.hasErrors())
            payloadErrorResolver.resolve(result);

        payload.setSubmitted(submitted);
        payload.setLastModifiedBy("");
        handler.ingest(submitted, "", payload);
    }

    /**
     * Info API for Tests, returns all the relevant data of a test.
     *
     * @param identifier Test Identifier required to fetch the test data
     * @return TestInfo object containing the test data based on the identifier
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "info", produces = MediaType.APPLICATION_JSON_VALUE)
    public TestInfo getTestInfoData(@RequestParam(value = "id")
                                    @NotEmpty String identifier) {
        return testService.info(identifier);
    }


    /**
     * Finalizes the test based on the identifier
     * <p>
     * Once a Test is finalized, Administrator can no longer edit it,
     * and the test will be available in the marketplace for partners
     * to purchase.
     * </p>
     *
     * @param identifier ID of Test to be finalized
     * @return Success Message with ACCEPTED status
     * @throws AlreadyExistsException If the test is already finalized
     * @throws NotFoundException      If the test is not found
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("info/finalize")
    public String finalizeTest(@RequestParam(value = "id")
                               @NotEmpty String identifier) throws AlreadyExistsException, NotFoundException {
        testService.finalizeTest(identifier, "");

        return "Test finalized successfully";
    }


    /**
     * Returns Saved Test data so that user can continue with the creation process
     *
     * @param identifier Test Identifier required to fetch the saved test data
     * @return StagedTest object containing the saved test data based on the identifier
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "info/saved", produces = MediaType.APPLICATION_JSON_VALUE)
    public StagedTest getSavedTest(@RequestParam(value = "id") @NotEmpty String identifier) {
        return stagingService.fetch(identifier);
    }

    @DeleteMapping("info/tables/delete")
    public ResponseEntity<WeightedResponse> deleteTable(@RequestParam(value = "id") String id) {
        return ResponseEntity.accepted().body(new WeightedResponse("Table deleted successfully",
                modificationService.deleteTable(id, "")));
    }

    @DeleteMapping("info/scores/delete")
    public ResponseEntity<WeightedResponse> deleteScore(@RequestParam(value = "id") String id) {
        return ResponseEntity.accepted().body(new WeightedResponse("Score removed successfully",
                modificationService.removeScore(id, "")));
    }


    @PutMapping("info/tables/populate")
    public ResponseEntity<WeightedResponse> addScores(@RequestBody @Valid NewScore payload,
                                                      BindingResult result) {
        if (result.hasErrors())
            payloadErrorResolver.resolve(result);

        return ResponseEntity.accepted().body(new WeightedResponse(
                "Scores added successfully",
                modificationService.addScore(payload, "")));
    }


    /**
     * Discards Saved Tests, deletes the test from the database.
     * These tests are not finalized and are not available for purchase.
     *
     * <p>
     * IDs of the tests to be discarded are passed as a comma separated string,
     * which is then fed to the service to delete the saved tests.
     * </p>
     *
     * @param payload IDs of tests to be discarded, passed as a comma separated string
     * @return Success Message with ACCEPTED status
     */
    @DeleteMapping(value = "saved/discard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> discardTest(@RequestParam(value = "ids") @NotEmpty String payload) {

        String[] ids = payload.split(", ");

        int discarded = stagingService.discard(ids);

        if (discarded < 1) {
            return ResponseEntity.badRequest().body("Couldn't discard Tests");
        } else if (ids.length == 1) {
            return ResponseEntity.accepted().body("Test discarded Successfully");
        }

        return ResponseEntity.accepted().body(discarded + " Tests discarded Successfully");
    }

    @GetMapping("marketplace")
    public ResponseEntity<Page<TestsMinimal>> testMarketPlace(@RequestParam("partner") String partnerId) {

        Partner partner = partnerService.fetch(partnerId);
        return ResponseEntity.ok(testService.marketplace(Pageable.unpaged(), partner));
    }

    @GetMapping("marketplace/search")
    public ResponseEntity<List<TestSearch>> testMarketPlaceSearch(@RequestParam("partner") String partnerId) {
        Partner partner = partnerService.fetch(partnerId);
        return ResponseEntity.ok(testService.marketplace(Pageable.unpaged(), partner)
                .stream().map(TestSearch::from).toList());
    }

    @GetMapping(value = "list")
    public ResponseEntity<Page<TestsMinimal>> getTests(@RequestParam("offset") int offset,
                                                       @RequestParam("size") int size,
                                                       @RequestParam(value = "status", required = false) String status) {

        if (status == null || status.isBlank())
            return ResponseEntity.ok(testService.list(Pageable.unpaged()));

        else return ResponseEntity.ok(testService.list(Pageable.unpaged(), status));
    }


    /**
     * Returns paged list of Saved tests based on page number and size.
     *
     * @param offset Page number to start from.
     * @param size   Size Of Page.
     * @return Paged List of Saved Tests.
     */
    @GetMapping(value = "list/saved")
    public ResponseEntity<Page<SavedTestsMinimal>> getStagedTests(@RequestParam("offset") int offset,
                                                                  @RequestParam("size") int size) {

        return ResponseEntity.ok(stagingService.list(Pageable.unpaged()));
    }


    @PostMapping("info/interpretations/create")
    public ResponseEntity<WeightedResponse> createInterpretations(@RequestBody List<NewInterpretation> payload) {
        if (payload.isEmpty())
            return ResponseEntity.badRequest().body(new WeightedResponse("No interpretations to create", 0));
        return ResponseEntity.accepted().body(new WeightedResponse(
                "Interpretation created successfully",
                testService.createInterpretation(payload, "")));
    }

    @DeleteMapping("info/interpretations/delete")
    public ResponseEntity<WeightedResponse> deleteInterpretations(@RequestParam("test") String test,
                                                                  @RequestParam("interpretation") String interpretation,
                                                                  @RequestParam("type") InterpretationType type) {
        if (test.isEmpty() || interpretation.isEmpty())
            return ResponseEntity.badRequest().body(new WeightedResponse("No interpretations to create", 0));
        return ResponseEntity.accepted().body(new WeightedResponse(
                "Interpretation created successfully",
                modificationService.deleteInterpretation(test, interpretation, type, "")));
    }


    @GetMapping("info/interpretations/list")
    public ResponseEntity<List<InterpretationSearchResponse>> searchInterpretations(@RequestParam("testId") String testId,
                                                                                    @RequestParam("type") InterpretationType type) {
        return ResponseEntity.ok(testService.searchInterpretations(testId, type));
    }


    @PutMapping("edit")
    public ResponseEntity<WeightedResponse> editSavedTest(@RequestBody EditTest test, String admin) {
        return ResponseEntity.accepted().body(new WeightedResponse("Test Modified Successfully", modificationService.update(test, admin)));
    }

    @PutMapping("edit/credits")
    public String updateCredits(@RequestBody EditCredit payload) {
        modificationService.modifyCredits(payload, "");
        return "Test Credits has been modified";
    }


    /**
     * Updates the status of the test as obtained on the
     * basis of Identifier.
     *
     * @param payload Status Modification Payload containing the test identifier and status
     * @return Success Message with ACCEPTED status
     * @throws NotFoundException If the test is not found
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("edit/status")
    public String updateTestStatus(@RequestBody StatusModificationPayload payload) throws NotFoundException {
        modificationService.updateTestStatus(payload, "");
        return "Test Status Updated Successfully";
    }

    @GetMapping("search/id/availability")
    public boolean checkIDAvailability(@RequestParam("id") String id) {
        return testService.checkIDAvailability(id);

    }

}


