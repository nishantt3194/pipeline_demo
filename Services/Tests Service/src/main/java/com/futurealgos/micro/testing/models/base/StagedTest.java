/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.micro.testing.dto.payload.tests.NewQuestion;
import com.futurealgos.micro.testing.dto.payload.tests.NewSubscale;
import com.futurealgos.micro.testing.models.embedded.QueuedObject;
import com.futurealgos.micro.testing.models.embedded.TestCredit;
import com.futurealgos.micro.testing.utils.enums.*;
import com.futurealgos.micro.testing.utils.mappings.BaseMap;
import com.futurealgos.micro.testing.utils.mappings.TestMap;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "staged_tests")
public class StagedTest {
    @Field(name = "max_age_year")
    @JsonProperty("maxAgeYear")
    public Integer maxAgeYear;
    @Id
    private String id;
    @Indexed
    @Field(TestMap.TEST_NAME)
    @JsonProperty("name")
    private String testName;

    @JsonProperty("author")
    @Field(name = "author")
    private String author;

    @JsonProperty("introduction")
    @Field(name = "introduction")
    private String introduction;

    @Field(TestMap.DESCRIPTION)
    @JsonProperty("description")
    private String description;
    @Field(TestMap.INSTRUCTIONS)
    @JsonProperty("instructions")
    private String instructions;

    @Field(TestMap.OVERVIEW)
    @JsonProperty("overview")
    private String overview;
    @Indexed
    @Field(TestMap.CATEGORY)
    @JsonProperty("category")
    private String testCategory;
    @Field(TestMap.ACCESS_LEVEL)
    @JsonProperty("accessLevel")
    private AccessLevelEnum accessLevel;
    @Field(TestMap.ADMINISTRATION_TYPE)
    @JsonProperty("administrationType")
    private AdministrationEnum administrationType;
    @Field(name = "age")
    private List<Integer> age;
    @Field(name = "min_age_month")
    @JsonProperty("minAgeMonth")
    private Integer minAgeMonth;
    @Field(name = "max_age_month")
    @JsonProperty("maxAgeMonth")
    private Integer maxAgeMonth;
    @Field(name = "min_age_year")
    @JsonProperty("minAgeYear")
    private Integer minAgeYear;
    @Field(TestMap.TEST_TYPE)
    @JsonProperty("testType")
    private TimingType testType;

    @Field(TestMap.TOTAL_TIME)
    @JsonProperty("totalTime")
    private Integer totalTime;

    @Field(TestMap.MODE)
    @JsonProperty("mode")
    private Mode mode;

    @Field(TestMap.SUBSCALE_MODE)
    @JsonProperty("subscaleMode")
    private Mode subscaleMode;

    @Field(TestMap.TEMPLATES)
    @Builder.Default
    @JsonProperty("templates")
    private Set<String> templates = new HashSet<>();

    @Field(TestMap.LANGUAGE)
    @JsonProperty("language")
    private String language;

    @Field(TestMap.NORMS)
    @Builder.Default
    @JsonProperty("norms")
    private List<String> norms = new ArrayList<>();

    @Field(TestMap.QUESTIONS)
    @JsonProperty("questions")
    private List<NewQuestion> questions;

    @Field(TestMap.SUBSCALES)
    @JsonProperty("subscales")
    private List<NewSubscale> subscales;


    @Field(TestMap.DEFAULT_SUBSCALE)
    @JsonProperty("defaultSubscale")
    private NewSubscale defaultSubscale;

    @Field
    @Builder.Default
    private Boolean submitted = false;

    @Transient
    @JsonIgnore
    @Builder.Default
    private Boolean attachmentsCompleted = false;

    @Field(name = "object_queue")
    @Builder.Default
    @JsonProperty("objectQueue")
    private Set<QueuedObject> queuedObject = new HashSet<>();

    @Field(BaseMap.CREATED_ON)
    private Date createdOn;

    @Field(BaseMap.UPDATED_ON)
    private Date modifiedOn;

    @Field(BaseMap.CREATED_BY)
    private String createdBy;

    @Field
    private String logo;
    @Field(TestMap.CREDITS)
    @JsonProperty("credits")
    private Double credits;

    @Field(BaseMap.UPDATED_BY)
    private String lastModifiedBy;

    @PersistenceCreator
    public StagedTest(String id, String testName,
                      String author, String description,
                      String instructions, String overview,
                      String introduction,
                      String testCategory, AccessLevelEnum accessLevel,
                      AdministrationEnum administrationType,
                      List<Integer> age, Integer minAgeMonth,
                      Integer maxAgeMonth, Integer minAgeYear,
                      Integer maxAgeYear, TimingType testType,
                      Integer totalTime, Mode mode,
                      Set<String> templates, String language,
                      List<String> norms, List<NewQuestion> questions,
                      List<NewSubscale> subscales, NewSubscale defaultSubscale,
                      Set<QueuedObject> queuedObject, Date createdOn,
                      Date modifiedOn, String createdBy, String lastModifiedBy, Double credits) {
        this.id = id;
        this.testName = testName;
        this.description = description;
        this.instructions = instructions;
        this.introduction=introduction;
        this.author=author;
        this.overview=overview;
        this.testCategory = testCategory;
        this.accessLevel = accessLevel;
        this.administrationType = administrationType;
        this.age = age;
        this.minAgeMonth = minAgeMonth;
        this.maxAgeMonth = maxAgeMonth;
        this.minAgeYear = minAgeYear;
        this.maxAgeYear = maxAgeYear;
        this.testType = testType;
        this.totalTime = totalTime;
        this.mode = mode;
        this.templates = templates;
        this.language = language;
        this.norms = norms;
        this.questions = questions;
        this.subscales = subscales;
        this.defaultSubscale = defaultSubscale;
        this.queuedObject = queuedObject;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.credits = credits;
    }

    public void evaluateAttachments() {
        this.attachmentsCompleted = true;
        for (QueuedObject object : queuedObject) {
            if (object.getId() == null) {
                this.attachmentsCompleted = false;
                return;
            }
        }
    }

    public Test.TestBuilder shell() {
        List<String> mandatoryTemplates = List.of("Raw Score", "Qualitative Descriptor", "Detailed Interpretation");
        Integer minAge = (this.minAgeYear * 12) + this.minAgeMonth;
        Integer maxAge = (this.maxAgeYear * 12) + this.maxAgeMonth;

        templates.addAll(mandatoryTemplates);

        return Test.builder()
                .testId(id)
                .testName(testName)
                .description(description)
                .instructions(instructions)
                .accessLevel(accessLevel)
                .administrationType(administrationType)
                .minAge(minAge)
                .maxAge(maxAge)
                .testType(testType)
                .totalTime(totalTime)
                .mode(mode)
                .status(TestStatus.CREATED)
                .noOfTimeReferred(0)
                .templates(templates)
                .credits(new TestCredit(credits))
                .author(author)
                .introduction(introduction)
                .overview(overview);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("testName", testName)
                .append("description", description)
                .append("instructions", instructions)
                .append("testCategory", testCategory)
                .append("accessLevel", accessLevel)
                .append("administrationType", administrationType)
                .append("age", age)
                .append("minAgeMonth", minAgeMonth)
                .append("maxAgeMonth", maxAgeMonth)
                .append("minAgeYear", minAgeYear)
                .append("maxAgeYear", maxAgeYear)
                .append("testType", testType)
                .append("totalTime", totalTime)
                .append("mode", mode)
                .append("credits", credits)
                .append("templates", templates)
                .append("language", language)
                .append("norms", norms)
                .append("questions", questions)
                .append("subscales", subscales)
                .append("defaultSubscale", defaultSubscale)
                .append("attachmentsCompleted", attachmentsCompleted)
                .append("objectQueues", queuedObject)
                .append("overview",overview)
                .toString();
    }
}
