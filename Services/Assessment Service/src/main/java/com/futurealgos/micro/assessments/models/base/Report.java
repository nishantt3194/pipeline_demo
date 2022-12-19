package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.embedded.InterpretationReport;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@Document(collection = "reports")
public class Report extends BaseEntity {

    @DocumentReference(collection = "assignee")
    private Assignee assignee;

    @DocumentReference(collection = "test")
    private Test Test;

    @Field("reason")
    private String reason;

    @Field("unique_id")
    private String uniqueId;

    @Field("suggestion")
    private String suggestion;

    @Field("examinee_name")
    private String examineeName;

    @Field("dob")
    private LocalDate dob;

    @Field("introduction")
    private String introduction;

    @Field("gender")
    private Gender gender;

    @Field("overview")
    private String overview;

    @DocumentReference(collection = "users")
    private User assessor;

    @Field("overall_intr_report")
    private InterpretationReport overallIntrReport;

    @Field("subscale_intr_report")
    private List<InterpretationReport> subscaleIntrReports;

    @DocumentReference(collection = "partner")
    private Partner partner;

    private Map<Integer,Integer> answerSheet;

    private Map<Question, Integer> scoreSheet;

    @Field("description")
    private String description;

}

