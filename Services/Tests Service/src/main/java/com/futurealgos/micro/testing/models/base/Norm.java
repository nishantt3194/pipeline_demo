/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.models.root.BaseEntity;

import com.futurealgos.micro.testing.utils.enums.NormTemplate;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "norms")
public class Norm extends BaseEntity implements Serializable {
    public enum Type {
        NUMBER, RANGE, TEXT;
    }

    @Serial
    private static final long serialVersionUID = -2399633596824248448L;

    @Field
    private String name;
    @Field
    private Type type;
    @Field
    private StatusEnum status;
    @Field
    private Boolean isPredefined;
    @Field
    private String description;
    @Field
    private NormTemplate template;
    @Field
    private List<Classification> classification;

}
