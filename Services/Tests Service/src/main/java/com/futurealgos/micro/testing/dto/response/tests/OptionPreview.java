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

/**
 * Package: com.futurealgos.micro.testing.dto.response.tests
 * Project: Prasad Psycho
 * Created On: 19/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
public class OptionPreview {

    private Integer index;
    private String description;

    public static OptionPreview build(Option option) {
        return OptionPreview.builder()
                .index(option.getIndex())
                .description(option.getDescription())
                .build();
    }
}
