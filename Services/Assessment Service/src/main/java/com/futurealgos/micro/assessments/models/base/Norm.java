/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.embedded.Classification;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.NormTemplate;
import com.futurealgos.micro.assessments.utils.enums.StatusEnum;
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
    @Serial
    private static final long serialVersionUID = -2399633596824248448L;
    public enum Type {
        NUMBER, RANGE, TEXT;
    }

    @Field
    private String name;
    @Field
    private Type type;
    @Field
    private StatusEnum status;
    @Field
    private Boolean isPredefined;
    @Field
    private NormTemplate template;
    @Field
    private List<? extends Classification> classification;

}
