/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.ExecAnswer;
import com.futurealgos.micro.assessments.dto.payload.ExecLogin;
import com.futurealgos.micro.assessments.dto.response.norms.RequiredDataMap;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.exceptions.UnauthorizedException;
import com.futurealgos.micro.assessments.models.base.AnswerSheet;
import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.models.embedded.AssessmentToken;
import com.futurealgos.micro.assessments.models.embedded.RequiredDataHolder;
import com.futurealgos.micro.assessments.repos.AnswerSheetRepo;
import com.futurealgos.micro.assessments.repos.AssigneeRepo;
import com.futurealgos.micro.assessments.utils.enums.LinkType;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import com.futurealgos.micro.assessments.utils.specs.services.InternalUpdateSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class AssigneeService implements FetcherSpec<Assignee>, InternalUpdateSpec<Assignee> {

    @Autowired
    AssigneeRepo assigneeRepo;
    @Autowired
    AssessmentService assessmentService;
    @Autowired
    AnswerSheetRepo answerSheetRepo;

    @Override
    public Assignee fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Assignee fetch(ObjectId identifier) {
        return assigneeRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find any Assignee with ID " + identifier));
    }

    @Override
    public List<Assignee> fetchAll(List<String> identifier) {
        List<Assignee> result = new ArrayList<>();
        assigneeRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    public Assignee fetch(String arId, List<String> uid) {
        AssessmentRequest request = assessmentService.fetch(arId);
        return assigneeRepo.findByRequestAndUidIsIn(request, uid)
                .orElseThrow(() -> new NotFoundException("Could not find Assignee details"));
    }

    public ExecLogin fetchLoginDetails(String arId, String uId) {
        AssessmentRequest request = assessmentService.fetch(arId);
        Assignee assignee = assigneeRepo.findByRequestAndUidIsIn(request, List.of(uId)).orElse(null);
        if(assignee == null && !request.getLinkType().equals(LinkType.OPEN))
            throw new NotFoundException("Could not find details with ID " + uId);

        if(assignee != null && assignee.getExecState().equals(Assignee.ExecState.COMPLETED))
            throw new UnauthorizedException("Assignee with ID " + uId + " has already completed the assessment");

        return assignee != null ? ExecLogin.of(assignee) : null;
    }

    public List<Assignee> create(String reqId, List<Examinee> examinees, String admin) {
        return create(assessmentService.fetch(reqId), examinees, admin);
    }

    public List<Assignee> create(AssessmentRequest request, List<Examinee> examinees, String admin) {
        if(request.getLinkType().equals(LinkType.OPEN))
            throw new IllegalStateException("Cannot pre build assignee data for open links");
        List<Assignee> assignees = new ArrayList<>();
        for (Examinee examinee : examinees) {
            Assignee assignee = Assignee.build(examinee, request.getNorms());
            assignee.setRequest(request);
            assignee = assigneeRepo.save(assignee, admin);
            AnswerSheet sheet = AnswerSheet.builder()
                    .assessmentRequest(request)
                    .assignee(assignee)
                    .build();
            sheet.buildSheet(request.getTest());

            sheet = answerSheetRepo.save(sheet, admin);

            assignee.setAnswerSheet(sheet);
            assignees.add(assigneeRepo.save(assignee, admin));
        }

        return assignees;
    }

    public void generateToken(String id) {
        Assignee assignee = fetch(id);
        assignee.setToken(AssessmentToken.generate());
        assigneeRepo.save(assignee);
    }

    public Assignee generateToken(Assignee assignee) {
        assignee.setToken(AssessmentToken.generate());
        return assigneeRepo.save(assignee);
    }

    public void regenerate(String id){
        Assignee assignee = fetch(id);
        assignee.setToken(AssessmentToken.generate());
        assigneeRepo.save(assignee);
    }

    public void revoke(String id){
        Assignee assignee = fetch(id);

        assignee.getToken().setToken(null);
        assignee.getToken().setStatus(AssessmentToken.Status.REVOKED);

        assigneeRepo.save(assignee);
    }

    public void terminate(String id){
        Assignee assignee = fetch(id);

        assignee.getToken().setToken(null);
        assignee.getToken().setStatus(AssessmentToken.Status.TERMINATED);
        assigneeRepo.save(assignee);

        // TODO: complete the termination flow
    }

    public void mergeRequiredData(Assignee assignee, Set<RequiredDataMap> data) {
        Set<String> ids = data.stream().map(RequiredDataMap::getId).collect(Collectors.toSet());
        for (RequiredDataHolder holder: assignee.getData()) {
            if(ids.contains(holder.getId())) {
                RequiredDataMap map = data.stream().filter(d -> d.getId().equals(holder.getId())).findFirst().get();
                holder.setValue(map.getValue());
            }
        }

        assigneeRepo.save(assignee);
    }

    @Override
    public Assignee update(Assignee payload, String admin) {
        return assigneeRepo.save(payload, admin);
    }

    public AnswerSheet updateSheet(ExecAnswer payload, String sheetId) {
        AnswerSheet sheet = answerSheetRepo.findById(new ObjectId(sheetId))
                .orElseThrow(() -> new NotFoundException("Could not find any sheet with ID " + sheetId));
        sheet.getAnswers().put(payload.id(), payload.clear() ? null: payload.answer());

        return answerSheetRepo.save(sheet, "");
    }
}
