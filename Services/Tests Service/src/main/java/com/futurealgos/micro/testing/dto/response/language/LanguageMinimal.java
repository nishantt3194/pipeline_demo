/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.language;

import com.futurealgos.micro.testing.models.base.LanguageStore;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class LanguageMinimal extends ListResponse {

    public String code;
    public String tag;

    public static LanguageMinimal build(LanguageStore language) {
        return LanguageMinimal.builder()
                .identifier(language.getId().toHexString())
                .code(language.getCode())
                .tag(language.getTag())
                .build();
    }

}
