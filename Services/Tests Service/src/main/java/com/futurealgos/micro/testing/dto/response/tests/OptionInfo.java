/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;


import com.futurealgos.micro.testing.models.embedded.Option;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class OptionInfo implements Serializable {

    private Integer index;
    private String description;
    private Integer score;

    public static OptionInfo build(Option option) {
        return OptionInfo.builder()
                .index(option.getIndex())
                .description(option.getDescription())
                .score(option.getScore())
                .build();
    }
}
