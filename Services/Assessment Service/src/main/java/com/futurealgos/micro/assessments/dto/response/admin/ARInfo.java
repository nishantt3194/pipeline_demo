/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@SuperBuilder
public final class ARInfo extends InfoResponse<AssessmentRequest, ARInfo> {

    public  String requestId;

    public  String test;

    public  Boolean bounded;

    public  Integer limit;

    public  Integer completedCounter;

    public  Integer activeCounter;

    public  Date expiry;

    public  String type;

    public  String linkType;

    public  Date start;

    public  String status;

    public  Map<String,String> attributes;

    public List<AssigneeInfo> assignees;

    public static ARInfo build(AssessmentRequest request) {

        return ARInfo.builder().requestId(request.getRequestId())
                .test(request.getTest().getTestId())
                .bounded(request.getBounded())
                .limit(request.getLimit())
                .completedCounter(request.getCompletedCounter())
                .activeCounter(request.getActiveCounter())
                .expiry(request.getExpiry())
                .type(request.getType().name())
                .linkType(request.getLinkType().name())
                .start(request.getStart())
                .status(request.getStatus().name())
                .attributes(request.getAttributes())
                .assignees(request.getAssignees().stream().map(AssigneeInfo::from).toList()).build();
    }
}
