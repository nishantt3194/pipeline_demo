/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.resources;

import com.futurealgos.micro.assessments.dto.payload.NewAssessment;
import com.futurealgos.micro.assessments.dto.response.admin.ARInfo;
import com.futurealgos.micro.assessments.dto.response.admin.ARMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.AssessmentSearch;
import com.futurealgos.micro.assessments.dto.response.admin.TestSearch;
import com.futurealgos.micro.assessments.dto.response.exec.TestPreview;
import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.models.base.Group;
import com.futurealgos.micro.assessments.services.*;
import com.futurealgos.micro.assessments.utils.enums.LinkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.endpoints.resources
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@RestController
@RequestMapping("/")
public class AssessmentResource {


    @Autowired
    TestPreviewService testPreviewService;
    @Autowired
    AssessmentService assessmentService;
    @Autowired
    AssigneeService assigneeService;
    @Autowired
    GroupService groupService;
    @Autowired
    ExamineeService examineeService;
    @Autowired
    TestService testService;


    /**
     * Preview API for Tests, returns a preview data of Test.
     *
     * @param identifier Test Identifier required to fetch the test data
     * @return TestInfo object containing the test data based on the identifier
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "preview", produces = MediaType.APPLICATION_JSON_VALUE)
    public TestPreview getTestsPreviewData(@RequestParam(value = "id") @NotNull String identifier) {
        return testPreviewService.fetchPreview(identifier);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody NewAssessment assessment) throws ParseException {
        AssessmentRequest request = assessmentService.create(assessment, "");

        List<Examinee> examinees;

        if (request.getLinkType().equals(LinkType.GROUP)) {
            Group group = groupService.fetch(assessment.group());
            examinees = group.getExaminees();
            request.getAttributes().put("group", group.getName());
            request.setLimit(examinees.size());

            request = assessmentService.update(request, "");

            request.setAssignees(assigneeService.create(request, examinees, ""));
        } else if (request.getLinkType().equals(LinkType.INDIVIDUAL)) {
            examinees = examineeService.fetchAll(assessment.examinees());
            request.setLimit(examinees.size());

            request = assessmentService.update(request, "");
            request.setAssignees(assigneeService.create(request, examinees, ""));

        } else {
            request.setLimit(assessment.limit());
        }
        assessmentService.update(request, "");

        return new ResponseEntity<>("Assessment Created Successfully", HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ARInfo info(@RequestParam(value = "id") String identifier) {
        return assessmentService.info(identifier);
    }

    @GetMapping(value = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ARMinimal>> list(
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "size", required = false) Integer size) {
//        if (paged)
//            return ResponseEntity.ok(assessmentService.list(PageRequest.of(offset, size)));

        return ResponseEntity.ok(assessmentService.list(Pageable.unpaged()));
    }

    @PostMapping(value = "token/regenerate")
    public String tokenRegeneration(@RequestParam(value = "id") String identifier) {
        assigneeService.regenerate(identifier);

        return "Token Regenerated";
    }

    @GetMapping(value = "token/revoke")
    public String tokenRevoke(@RequestParam(value = "id") String identifier) {
        assigneeService.revoke(identifier);

        return "Token Revoked";
    }

    @GetMapping(value = "token/terminate")
    public String tokenTermination(@RequestParam(value = "id") String identifier) {
        assigneeService.terminate(identifier);

        return "Token Terminated";
    }

    /**
     * @return
     */

    @GetMapping(value = "tests/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TestSearch>> searchAllTest() {
        return ResponseEntity.ok(testService.search());
    }

    /**
     * @return
     */
    @GetMapping(value = "assessment/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AssessmentSearch>> searchAllAssessment() {
        return ResponseEntity.ok(assessmentService.search());
    }


}
