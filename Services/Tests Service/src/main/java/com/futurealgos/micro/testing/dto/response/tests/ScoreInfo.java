/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.embedded.Score;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Builder
public class ScoreInfo implements Serializable {
    private String id;
    private String rawScore;
    private String qualitativeDescriptor;
    private String detailedInterpretation;
    private Map<String, String> extras;

    public static ScoreInfo build(Score score) {
        return ScoreInfo.builder()
                .id(score.getId().toHexString())
                .rawScore(score.getRawScore())
                .qualitativeDescriptor(score.getQualitativeDescriptor().getDescription())
                .detailedInterpretation(score.getDetailedInterpretation().getInterpretationName())
                .extras(score.getAdditionalScore())
                .build();
    }
}
