/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.base.Test;
import com.futurealgos.micro.testing.utils.enums.Mode;
import com.futurealgos.micro.testing.utils.enums.TestStatus;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@SuperBuilder
public class TestInfo extends InfoResponse<Test, TestInfo> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6540500923507713015L;
    private String identifier;
    private String id;
    private String name;
    private String description;
    private String instructions;
    private String category;
    private TestStatus status;
    private String mode;
    private String accessLevel;
    private String administrationType;
    private Integer minAge;
    private Integer maxAge;
    private String language;
    private String testType;
    private Integer totalTime;
    private List<String> norms;
    private Set<String> templates;
    private Double credits;
    private String introduction;
    private String author;
    private List<QuestionInfo> questions;
    private List<SubscaleInfo> subscales;
    private List<ScoreTableInfo> tables;
    private List<QualitativeDescriptorInfo> qualitativeDescriptors;
    private List<DetailedInterpretationInfo> detailedInterpretations;
    private String overview;

    public static TestInfo populate(Test entity) {
        System.out.println(entity.getQuestions().size());

        return TestInfo.builder()
                .identifier(entity.getId().toHexString())
                .id(entity.getTestId())
                .name(entity.getTestName())
                .status(entity.getStatus())
                .description(entity.getDescription())
                .instructions(entity.getInstructions())
                .category(entity.getTestCategory().getName())
                .mode(entity.getMode().getName())
                .accessLevel(entity.getAccessLevel().getName())
                .administrationType(entity.getAdministrationType().getName())
                .minAge(entity.getMinAge())
                .maxAge(entity.getMaxAge())
                .language(entity.getLanguage().getTag())
                .testType(entity.getTestType().getName())
                .totalTime(entity.getTotalTime())
                .author(entity.getAuthor())
                .introduction(entity.getIntroduction())
                .credits(entity.getCredits().getValue())
                .norms(entity.getNorms()
                        .stream().map(Norm::getName).toList())
                .templates(entity.getTemplates())
                .qualitativeDescriptors(entity.getQualitativeDescriptors() != null ?
                        entity.getQualitativeDescriptors().stream().map(QualitativeDescriptorInfo::build).toList()
                        : new ArrayList<>())
                .detailedInterpretations(entity.getDetailedInterpretations() != null ?
                        entity.getDetailedInterpretations().stream().map(DetailedInterpretationInfo::build).toList()
                        : new ArrayList<>())
                .questions(entity.getQuestions()
                        .stream().map(QuestionInfo::build).toList())
                .subscales(entity.getMode().equals(Mode.USER_DEFINED) ? entity.getSubscales()
                        .stream()
                        .map(SubscaleInfo::build).toList() : new ArrayList<>())
                .tables(entity.getScoreTables().stream()
                        .map(ScoreTableInfo::build).toList())
                .overview(entity.getOverview())
                .build();
    }
}
