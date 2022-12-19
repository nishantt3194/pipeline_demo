/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.models.base.Test;
import com.futurealgos.micro.testing.models.embedded.TestCredit;
import com.futurealgos.micro.testing.utils.enums.*;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
public record NewTest(
        @NotNull @NotBlank String testId,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String description,
        @NotNull @NotBlank String category,
        @NotNull AccessLevelEnum accessLevel,
        @NotNull AdministrationEnum administrationType,
        @NotNull @NotBlank String language,
        @NotNull List<Integer> age,
        @NotNull TimingType testType,
        Integer totalTime,
        @NotNull Mode mode,
        String instructions,

        String introduction,

        String author,
        String overview,
        Set<String> templates,
        List<NewSubscale> subscales,
        List<String> norms,
        Double credits) implements Serializable {
    //TODO:

    public Test convert() {
        return shell().build();
    }

    public Test.TestBuilder shell() {
        List<String> mandatoryTemplates = List.of("Raw Score", "Qualitative Descriptor", "Detailed Interpretation");
        for (String template :
                mandatoryTemplates) {
            if (!templates.contains(template))
                templates.add(template);
        }

        return Test.builder()
                .testId(testId)
                .testName(name)
                .description(description)
                .instructions(instructions)
                .accessLevel(accessLevel)
                .introduction(introduction)
                .author(author)
                .administrationType(administrationType)
                .minAge(age.get(0))
                .maxAge(age.get(1))
                .testType(testType)
                .totalTime(totalTime)
                .mode(mode)
                .status(TestStatus.STAGED)
                .noOfTimeReferred(0)
                .credits(new TestCredit(credits))
                .templates(templates)
                .overview(overview);
    }
}
