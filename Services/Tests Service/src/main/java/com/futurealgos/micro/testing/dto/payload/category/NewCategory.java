/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.category;

import com.futurealgos.micro.testing.models.base.TestCategory;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record NewCategory(
        @NotNull @NotEmpty String name,
        @NotNull @NotEmpty String description) {

    public TestCategory convert() {
        return TestCategory.builder()
                .name(name)
                .description(description)
                .status(StatusEnum.ACTIVE)
                .build();
    }
}
