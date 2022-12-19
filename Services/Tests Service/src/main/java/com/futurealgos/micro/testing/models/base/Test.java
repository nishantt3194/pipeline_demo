/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.futurealgos.micro.testing.models.embedded.TestCredit;
import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.enums.*;
import com.futurealgos.micro.testing.utils.mappings.TestMap;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test")
public class Test extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -275074534002560109L;

    @Indexed(unique = true)
    @Field(TestMap.TEST_ID)
    private String testId;

    @Indexed
    @Field(TestMap.TEST_NAME)
    private String testName;

    @Field(TestMap.DESCRIPTION)
    private String description;

    @Field(TestMap.INSTRUCTIONS)
    private String instructions;

    @Field(TestMap.OVERVIEW)
    private String overview;

    @Indexed
    @Field(TestMap.CATEGORY)
    @DocumentReference(lazy = true, collection = "test_category")
    private TestCategory testCategory;

    @Field(TestMap.ACCESS_LEVEL)
    private AccessLevelEnum accessLevel;

    @Field(TestMap.ADMINISTRATION_TYPE)
    private AdministrationEnum administrationType;

    @Field(TestMap.MIN_AGE)
    private Integer minAge;

    @Field(TestMap.MAX_AGE)
    private Integer maxAge;

    @Field(TestMap.TEST_TYPE)
    private TimingType testType;

    @Field(TestMap.SUBSCALE_MODE)
    private TimingType subscaleMode;

    @Field(TestMap.TOTAL_TIME)
    private Integer totalTime;

    @Field(TestMap.LOGO)
    @DocumentReference(collection = "document_metadata")
    private DocumentMetadata logo;

    @Indexed
    @Field(TestMap.STATUS)
    private TestStatus status;

    @Field(TestMap.MODE)
    private Mode mode;

    @Field(TestMap.TIMES_REFERRED)
    private long noOfTimeReferred;

    @Field(TestMap.TEMPLATES)
    @Builder.Default
    private Set<String> templates = new HashSet<>();

    @Field(TestMap.LANGUAGE)
    @DocumentReference(lazy = true, collection = "language_store")
    private LanguageStore language;

    @Field(TestMap.NORMS)
    @Builder.Default
    @DocumentReference(lazy = true, collection = "norms")
    private List<Norm> norms = new ArrayList<>();

    @Field(TestMap.SCORE_TABLES)
    @Builder.Default
    @DocumentReference(collection = "score_table")
    private List<ScoreTable> scoreTables = new ArrayList<>();

    @Field(TestMap.QUESTIONS)
    @Builder.Default
    @DocumentReference(collection = "question")
    private List<Question> questions = new ArrayList<>();

    @Field(TestMap.SUBSCALES)
    @Builder.Default
    @DocumentReference(collection = "subscale")
    private List<Subscale> subscales = new ArrayList<>();

    @Field(TestMap.DETAILED_INTERPRETATIONS)
    @Builder.Default
    @DocumentReference(collection = "detailed_interpretation")
    private List<DetailedInterpretation> detailedInterpretations = new ArrayList<>();

    @Field(TestMap.QUALITATIVE_DESCRIPTORS)
    @Builder.Default
    @DocumentReference(collection = "qualitative_descriptor")
    private List<QualitativeDescriptor> qualitativeDescriptors = new ArrayList<>();

    @Field(TestMap.DEFAULT_SUBSCALE)
    private Subscale defaultSubscale;

    @Field(TestMap.CREDITS)
    private TestCredit credits;

    @Field(TestMap.AUTHOR)
    private String author;

    @Field(TestMap.INTRODUCTION)
    private String introduction;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;

        return super.equals(o);
    }

    @JsonManagedReference
    public List<ScoreTable> getScoreTables() {
        return scoreTables;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("testId", testId)
                .append("testName", testName)
                .append("description", description)
                .append("instructions", instructions)
                .append("overview",overview)
                .append("testCategory", testCategory)
                .append("accessLevel", accessLevel)
                .append("administrationType", administrationType)
                .append("minAge", minAge)
                .append("maxAge", maxAge)
                .append("testType", testType)
                .append("totalTime", totalTime)
                .append("credits", credits)
                .append("logo", logo)
                .append("status", status)
                .append("mode", mode)
                .append("noOfTimeReferred", noOfTimeReferred)
                .append("templates", templates)
                .append("language", language)
                .append("norms", norms)
                .append("scoreTables", scoreTables)
                .append("questions", questions)
                .append("subscales", subscales)
                .append("defaultSubscale", defaultSubscale)
                .append("introduction",introduction)
                .append("author",author)
                .append("id", id.toHexString())
                .toString();
    }
}
