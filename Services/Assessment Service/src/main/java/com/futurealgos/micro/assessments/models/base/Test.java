/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;


import com.futurealgos.micro.assessments.models.embedded.TestCredit;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.*;
import com.futurealgos.micro.assessments.utils.mappings.TestMap;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "test")
public class Test extends BaseEntity implements Serializable {

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

    @Field("introduction")
    private String introduction;

    @Field("overview")
    private String overview;

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

    @Field(TestMap.LANGUAGE)
    @DocumentReference(lazy = true, collection = "language_store")
    private LanguageStore language;

    @Field(TestMap.NORMS)
    @Builder.Default
    @DocumentReference(lazy = true, collection = "norms")
    private Set<Norm> norms = new HashSet<>();

    @Field(TestMap.QUESTIONS)
    @Builder.Default
    @DocumentReference(lazy = true, collection = "question")
    private List<Question> questions = new ArrayList<>();

    @Field(TestMap.SUBSCALES)
    @Builder.Default
    @DocumentReference(lazy = true, collection = "subscale")
    private List<Subscale> subscales = new ArrayList<>();

    @Field(TestMap.SCORE_TABLES)
    @Builder.Default
    @DocumentReference(collection = "score_table")
    private List<ScoreTable> scoreTables = new ArrayList<>();

    @Field(TestMap.DEFAULT_SUBSCALE)
    private Subscale defaultSubscale;

    @Field(TestMap.CREDITS)
    private TestCredit credits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;

        return super.equals(o);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("testId", testId)
                .append("testName", testName)
                .append("description", description)
                .append("instructions", instructions)
                .append("minAge", minAge)
                .append("maxAge", maxAge)
                .append("testType", testType)
                .append("totalTime", totalTime)
                .append("credits", credits)
                .append("logo", logo)
                .append("status", status)
                .append("mode", mode)
                .append("language", language)
                .append("norms", norms)
                .append("questions", questions)
                .append("subscales", subscales)
                .append("defaultSubscale", defaultSubscale)
                .append("id", id.toHexString())
                .toString();
    }
}
