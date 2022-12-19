/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.language;

import com.futurealgos.micro.testing.models.base.LanguageStore;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class LanguageInfo extends InfoResponse<LanguageStore, LanguageInfo> {

    public String code;
    public String tag;
    public String description;

    public static LanguageInfo populate(LanguageStore entity) {
        return null;
    }
}
