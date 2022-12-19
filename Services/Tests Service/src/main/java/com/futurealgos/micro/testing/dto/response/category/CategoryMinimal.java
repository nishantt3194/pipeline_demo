/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.category;

import com.futurealgos.micro.testing.models.base.TestCategory;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.text.SimpleDateFormat;

@Getter
@Setter
@SuperBuilder
public class CategoryMinimal extends ListResponse {

    private String name;

    private String description;

    private String status;

    private String createdOn;

    private Integer counts;

    public static CategoryMinimal build(TestCategory category) {
        return CategoryMinimal.builder()
                .identifier(category.getId().toHexString())
                .name(category.getName())
                .counts(0)
                .description(category.getDescription())
                .status(category.getStatus().getName())
                .createdOn(new SimpleDateFormat("dd MMM yyyy").format(category.getCreatedOn()))
                .build();

    }


}
