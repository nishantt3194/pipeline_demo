/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.language;

import com.futurealgos.micro.testing.models.base.LanguageStore;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record NewLanguage(
        @NotNull @NotEmpty String code,
        @NotNull @NotEmpty String tag,
        @NotNull @NotEmpty String description) {

    public LanguageStore convert() {
        return LanguageStore.builder()
                .code(code)
                .tag(tag)
                .status(StatusEnum.ACTIVE)
                .description(description)
                .build();
    }
}
