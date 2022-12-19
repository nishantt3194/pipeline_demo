/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.ExecLogin;
import com.futurealgos.micro.assessments.dto.payload.SessionNotification;
import com.futurealgos.micro.assessments.dto.response.exec.SessionExchange;
import com.futurealgos.micro.assessments.dto.response.exec.SubscaleDataRef;
import com.futurealgos.micro.assessments.dto.response.exec.TestExecData;
import com.futurealgos.micro.assessments.dto.response.norms.PrerequisiteData;
import com.futurealgos.micro.assessments.dto.response.norms.RequiredDataMap;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.exceptions.UnauthorizedException;
import com.futurealgos.micro.assessments.models.base.*;
import com.futurealgos.micro.assessments.repos.SessionDataRepo;
import com.futurealgos.micro.assessments.utils.enums.ARStatus;
import com.futurealgos.micro.assessments.utils.enums.LinkType;
import com.futurealgos.micro.assessments.utils.enums.TimingType;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class ExecutionService {

    @Autowired
    AssessmentService assessmentService;
    @Autowired
    AssigneeService assigneeService;
    @Autowired
    TokenService tokenService;
    @Autowired
    SessionDataRepo sessionDataRepo;

    @Autowired
    TestService testService;

    ConcurrentHashMap<String, String> pendingSectionAction = new ConcurrentHashMap<>();

    public String login(ExecLogin payload) {
        Assignee assignee = assigneeService.fetch(payload.request(), List.of(payload.email(), payload.extn() + "-" + payload.phone()));
        if (assignee.getExecState().equals(Assignee.ExecState.IN_PROGRESS)) {
            if (!assignee.getToken().getToken().equals(payload.token()))
                throw new UnauthorizedException("Invalid token");
        }
        if (assignee.getExecState().equals(Assignee.ExecState.COMPLETED))
            throw new UnauthorizedException("Assessment is already Complete");

        if (assignee.getExecState().equals(Assignee.ExecState.NOT_STARTED) && assignee.getToken() == null)
            assignee = assigneeService.generateToken(assignee);

        Map<String, String> claims = new HashMap<>();
        claims.put("request", payload.request());
        claims.put("uid", assignee.getUid());
        claims.put("eid", assignee.getExaminee().getId().toHexString());
        claims.put("aid", assignee.getAnswerSheet().getId().toHexString());
        claims.put("name", assignee.getExaminee().getName());


        return tokenService.generate(assignee.getId().toHexString(),
                assignee.getToken().getToken(), claims);
    }

    public boolean checkValidity(String arId) {
        if (!ObjectId.isValid(arId)) return false;
        try {
            AssessmentRequest request = assessmentService.fetch(arId);
            if (request.getBounded() && new Date().before(request.getExpiry()) || request.getStatus().equals(ARStatus.COMPLETED))
                return false;
            if (request.getStatus().equals(ARStatus.INITIATED)) return true;
            if (!request.getLinkType().equals(LinkType.OPEN) && request.getStatus().equals(ARStatus.ONGOING))
                return true;
            else return !request.getBounded() && (request.getRemainingSlots() > 0);
        } catch (NotFoundException e) {
            return false;
        }
    }

    public Set<RequiredDataMap> fetchRequiredData(Assignee assignee) {
        Set<RequiredDataMap> requiredData = new HashSet<>();

        assignee.getData().stream().filter(holder -> holder.getValue() == null || holder.getValue().isEmpty())
                .map(RequiredDataMap::build)
                .forEach(requiredData::add);

        return requiredData;
    }

    @Transactional
    public PrerequisiteData fetchPrerequisiteData(Assignee assignee) {
        long emptyData = assignee.getData().stream().filter(d -> d.getValue() == null || d.getValue().isEmpty())
                .count();

        if (emptyData > 0) throw new UnauthorizedException("Required data for the assessment is not filled.");


        return PrerequisiteData.builder()
                .token(assignee.getToken().getToken())
                .instructions(assignee.getRequest().getTest().getInstructions())
                .build();

    }

    public TestExecData startTest(Assignee assignee) {
        TestExecData data = TestExecData.build(testService.fetch(assignee.getTestId()));
        data.buildAnswers(assignee.getAnswerSheet().getAnswers());
        if (data.getTiming().equals(TimingType.TIMED)) {
            data.setInitialTime(assignee.fetchElapsedTime());

            if (data.getSubscaleMode().equals(TimingType.TIMED)) {
                data.setCurrentSubscale(assignee.getSession() != null ? assignee.getSession().getCurrentSection() : null);
                data.setInitialSubscaleTime(assignee.fetchElapsedTimeForCurrentSubscale());

                if (assignee.getSession() == null || assignee.getSession().getSections() == null) return data;

                List<String> sectionIds = assignee.getSession().getSections().stream().map(SectionData::getSubscale).toList();
                List<SubscaleDataRef> refs = data.getSubscales().stream()
                        .filter(s -> sectionIds.contains(s.id)).toList();
                for (SectionData section : assignee.getSession().getSections()) {
                    SubscaleDataRef ref = refs
                            .stream().filter(s -> s.id.equals(section.getSubscale())).findFirst().orElse(null);
                    if (ref == null) continue;
                    ref.status = section.getStatus().equals(SectionData.Status.ONGOING) ? "started" : "completed";
                }
            }
        }

        return data;
    }

    public SessionNotification updateSessionState(SessionNotification payload) {
        Assignee assignee = assigneeService.fetch(payload.id());

        switch (payload.state()) {
            case CONNECTED -> {
                SessionData sessionData = assignee.initSession(payload.ip(), payload.timestamp());
                sessionData = sessionDataRepo.save(sessionData);
                assignee.setSession(sessionData);
                assignee.setExecState(Assignee.ExecState.IN_PROGRESS);
                if (this.pendingSectionAction.containsKey(assignee.getId().toHexString())) {
                    this.startSection(assignee, this.pendingSectionAction.get(assignee.getId().toHexString()));
                    this.pendingSectionAction.remove(assignee.getId().toHexString());
                }
            }
            case SUSPENDED -> {
                if (assignee.getExecState().equals(Assignee.ExecState.COMPLETED)) {
                    this.sessionDataRepo.save(assignee.terminateSession(payload.timestamp()), "");
                } else {
                    this.sessionDataRepo.save(assignee.suspendSession(payload.timestamp()), "");
                }
            }
            case DISCONNECTED -> {
            }

        }

        assignee = assigneeService.update(assignee, "");
        AssessmentRequest request = assessmentService.fetch(assignee.getRequest().getId());
        request.buildCounters();
        if (request.getActiveCounter() > 0) request.setStatus(ARStatus.ONGOING);
        else if (request.getCompletedCounter().equals(request.getLimit())) request.setStatus(ARStatus.COMPLETED);
        assessmentService.update(request, "");

        return payload;
    }

    public void submit(Assignee assignee) {
        assignee.setExecState(Assignee.ExecState.COMPLETED);
        assigneeService.update(assignee, "");
    }

    public SessionExchange startSection(Assignee assignee, String subscale) {
        Subscale sub = assignee.getRequest().getTest().getSubscales().stream()
                .filter(s -> s.getId().toHexString().equals(subscale)).findFirst().orElse(null);

        if (sub == null) return SessionExchange.shell();

        if (assignee.getSession() == null) {
            if (!this.pendingSectionAction.containsKey(assignee.getId().toHexString())) {
                this.pendingSectionAction.put(assignee.getId().toHexString(), subscale);
            }
        }
        else {
            Set<SectionData> assignedSections = assignee.getSession().getSections();
            if (assignedSections != null && assignedSections.stream()
                    .map(SectionData::getSubscale).anyMatch(s -> s.equals(subscale))) {
                return null;
            }

            assignee.getSession().setCurrentSection(subscale);

            SectionData section = SectionData.builder()
                    .startTime(new Date())
                    .subscale(subscale)
                    .status(SectionData.Status.ONGOING)
                    .timeLimit(Duration.ofMinutes(sub.getTotalTime()).toMillis())
                    .build();

            if (assignedSections != null) {
                assignedSections.add(section);
            } else {
                TreeSet<SectionData> sections = new TreeSet<>();
                sections.add(section);
                assignee.getSession().setSections(sections);
            }

            this.sessionDataRepo.save(assignee.getSession(), "");
        }

        assignee = assigneeService.fetch(assignee.getId());
        Test test = testService.fetch(assignee.getTestId());
        return SessionExchange.build(assignee, test);
    }

    public SessionExchange endSection(Assignee assignee, String subscale) {
        assignee.getSession().getSections().stream().filter(s -> s.getSubscale().equals(subscale))
                .forEach(s -> s.setStatus(SectionData.Status.COMPLETED));
        assignee.getSession().setCurrentSection(null);
        this.sessionDataRepo.save(assignee.getSession(), "");

        this.pendingSectionAction.remove(assignee.getId().toHexString());
        assignee = assigneeService.fetch(assignee.getId());
        Test test = testService.fetch(assignee.getTestId());
        return SessionExchange.build(assignee, test);
    }
}
