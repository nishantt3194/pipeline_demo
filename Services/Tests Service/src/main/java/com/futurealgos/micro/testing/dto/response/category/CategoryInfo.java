/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.category;

import com.futurealgos.micro.testing.models.base.TestCategory;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CategoryInfo extends InfoResponse<TestCategory, CategoryInfo> {

    private String name;

    private String description;

    private String status;

    private long totalTests;

    private String createdDate;

    public static CategoryInfo populate(TestCategory entity) {
        return CategoryInfo.builder()
                .identifier(entity.getId().toHexString())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }


}
