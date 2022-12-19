/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.score;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

public record NewScoreValues(

        @NotNull
        Double maxRawScore,
        Double minRawScore,
        @NotNull
        @NotBlank
        String detailedInterpretation,

        @NotNull
        @NotBlank
        String qualitativeDescriptor,
        HashMap<String, String> extraScores
) {

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("maxRawScore", maxRawScore)
                .append("minRawScore", minRawScore)
                .append("detailedInterpretation", detailedInterpretation)
                .append("qualitativeDescriptor", qualitativeDescriptor)
                .append("extraScores", extraScores)
                .toString();
    }
}
