/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.score;


import com.futurealgos.micro.testing.models.embedded.Score;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record NewScore(

        @NotBlank
        @NotNull
        String testId,

        @NotNull
        @NotBlank
        String scoreTable,

        @NotNull
        List<NewScoreValues> scores

) {

    public Score convert(NewScoreValues payload) {
        return Score.builder()
                .maxRawScore(payload.maxRawScore())
                .minRawScore(payload.minRawScore())
                .additionalScore(payload.extraScores())
                .build();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("testId", testId)
                .append("scoreTable", scoreTable)
                .append("scores", scores)
                .toString();
    }
}
