/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;


import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Builder
@Getter
@Setter
@Document(collection = "detailed_interpretation")
public class DetailedInterpretation extends BaseEntity implements Serializable {

    @Field(name = "interpretation_name")
    private String interpretationName;

    @Field(name = "details")
    private String details;

    @Field
    @DocumentReference(lazy = true, collection = "test")
    private Test test;

}
