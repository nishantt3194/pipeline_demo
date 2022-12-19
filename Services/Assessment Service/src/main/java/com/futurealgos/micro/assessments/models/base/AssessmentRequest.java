/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.dto.payload.NewAssessment;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.ARStatus;
import com.futurealgos.micro.assessments.utils.enums.LinkType;
import com.futurealgos.micro.assessments.utils.enums.Type;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.text.WordUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "assessment_request")
public class AssessmentRequest extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -3755421448455210116L;

    @Field("request_id")
    private String requestId;

    @DocumentReference(collection = "test")
    private Test test;

    @Field
    private Boolean bounded;

    @Field(name = "limit")
    private Integer limit;

    @Field(name = "completed_counter")
    private Integer completedCounter;

    @Field(name = "active_counter")
    private Integer activeCounter;

    @Field(name = "expiry")
    private Date expiry;

    @Field(name = "scheduleType")
    private Type type;

    @Field(name = "link_type")
    private LinkType linkType;

    @Field(name = "start")
    private Date start;

    @Field("status")
    private ARStatus status;

    @Field(name = "attributes")
    @Builder.Default
    private Map<String,String> attributes = new HashMap<>();

    @Field(name = "assignees")
    @Builder.Default
    @DocumentReference(collection = "assignee")
    private List<Assignee> assignees = new ArrayList<>();

    @DocumentReference(collection = "norm")
    private Set<Norm> norms;

    @DocumentReference(collection = "partner")
    private Partner partner;

    public void buildRequestId() {
        var testAbbr = WordUtils.initials(test.getTestName()).replaceAll("[^A-Za-z]+", "");
        this.requestId = "AS-%s-%s".formatted(RandomStringUtils.randomAlphanumeric(8), testAbbr);
    }

    public void buildCounters() {
        completedCounter = 0;
        activeCounter = 0;
        for(Assignee assignee : assignees) {
            if(assignee.getExecState() == Assignee.ExecState.COMPLETED) {
                completedCounter++;
            } else if(assignee.getExecState() == Assignee.ExecState.IN_PROGRESS) {
                activeCounter++;
            }
        }
    }

    public Integer getRemainingSlots() {
        return Math.max(limit - (completedCounter + activeCounter), 0);
    }

    public static AssessmentRequest build(NewAssessment payload) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date start = payload.scheduleType().equals(Type.SCHEDULED) ? df.parse(payload.start()) : null;
        Date end = payload.bounded() ? df.parse(payload.end()) : null;
        return AssessmentRequest.builder()
                .linkType(payload.linkType())
                .type(payload.scheduleType())
                .status(ARStatus.INITIATED)
                .bounded(payload.bounded())
                .limit(payload.linkType().equals(LinkType.OPEN) ? payload.limit() : null)
                .start(start)
                .expiry(end)
                .build();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestId", requestId)
                .append("test", test)
                .append("bounded", bounded)
                .append("limit", limit)
                .append("completedCounter", completedCounter)
                .append("activeCounter", activeCounter)
                .append("expiry", expiry)
                .append("type", type)
                .append("linkType", linkType)
                .append("start", start)
                .append("status", status)
                .append("attributes", attributes)
                .append("id", id)
                .toString();
    }
}
