/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.futurealgos.micro.testing.models.root.BaseEntity;

import com.futurealgos.micro.testing.utils.enums.StatusEnum;
import com.futurealgos.micro.testing.utils.mappings.CategoryMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test_category")
public class TestCategory extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 4102733227770857406L;

    @Field(CategoryMap.NAME)
    private String name;

    @Field(CategoryMap.DESCRIPTION)
    private String description;

    @Field(CategoryMap.STATUS)
    private StatusEnum status;

}
