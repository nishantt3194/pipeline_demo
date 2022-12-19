/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.Subscale;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscaleInfo {
    private String name;
    private String description;
    private String instructions;
    private Integer totalTime;
    private String type;
    private String status;

    public static SubscaleInfo build(Subscale subscale) {
        return SubscaleInfo.builder()
                .name(subscale.getName())
                .description((subscale.getDescription() == null || subscale.getDescription().length() < 1) ? "N/A" : subscale.getDescription())
                .instructions((subscale.getInstructions() == null || subscale.getInstructions().length() < 1) ? "N/A" : subscale.getInstructions())
                .totalTime(subscale.getTotalTime())
                .type(subscale.getType().getName())
                .build();
    }
}
