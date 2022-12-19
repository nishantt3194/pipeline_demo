/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.models.embedded.Session;
import com.futurealgos.micro.assessments.utils.specs.dto.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.text.SimpleDateFormat;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@SuperBuilder
@Getter
@Setter
public class ARMinimal extends ListResponse {

    public String requestId;

    public String test;

    public String remaining;

    public String start;

    public String expiry;

    public String status;

    public String assigneeId;

    public String state;

    public boolean reportGenerated;

    public String execDate;


    public static ARMinimal convert(AssessmentRequest request){
        return ARMinimal.builder()
                .identifier(request.getId().toHexString())
                .requestId(request.getRequestId())
                .start(new SimpleDateFormat("dd MMM yyyy, hh:mm a")
                        .format(request.getStart()))
                .expiry(new SimpleDateFormat("dd MMM yyyy, hh:mm a")
                        .format(request.getExpiry()))
                .test(request.getTest().getTestId())
                .remaining(request.getCompletedCounter()+"/"+request.getLimit())
                .status(request.getStatus().name())
                .build();
    }

    public static ARMinimal convert(Assignee assignee) {
        AssessmentRequest request = assignee.getRequest();
        SortedSet<Session> sessions = assignee.getSession() != null ?
                assignee.getSession().getSessionsHistory() : new TreeSet<>();
        return ARMinimal.builder()
                .identifier(request.getId().toHexString())
                .requestId(request.getRequestId())
                .expiry(new SimpleDateFormat("dd MMM yyyy, hh:mm a")
                        .format(request.getExpiry()))
                .test(request.getTest().getTestId())
                .remaining(request.getCompletedCounter()+"/"+request.getLimit())
                .status(request.getStatus().name())
                .state(assignee.getExecState().name())
                .assigneeId(assignee.getId().toHexString())
                .execDate(sessions.isEmpty() ? "N/A" :
                        new SimpleDateFormat("dd MMM yyyy, hh:mm a")
                                .format(sessions.first().getStart()))
                .reportGenerated(assignee.isReportGenerated())
                .build();
    }
}
