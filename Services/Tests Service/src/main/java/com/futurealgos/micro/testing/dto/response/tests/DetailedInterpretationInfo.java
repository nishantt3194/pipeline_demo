/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.DetailedInterpretation;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class DetailedInterpretationInfo {

    public String id;
    public String name;
    public String details;

    public static DetailedInterpretationInfo build(DetailedInterpretation detailedInterpretation) {
        return DetailedInterpretationInfo.builder()
                .id(detailedInterpretation.getId().toHexString())
                .details(detailedInterpretation.getDetails())
                .name(detailedInterpretation.getInterpretationName())
                .build();
    }
}
