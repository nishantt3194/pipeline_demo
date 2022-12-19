/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;


import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.enums.Mode;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;
import com.futurealgos.micro.testing.utils.enums.TimingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "subscale")
public class Subscale extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1505085855205848245L;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private String instructions;

    @Field("type")
    private TimingType type;

    @Field("total_time")
    private Integer totalTime;

    @Field
    @DocumentReference(collection = "test")
    private Test test;

    @Field("subscale_type")
    private Mode subScaleType;

    @Field
    private StatusEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Subscale)) return false;

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
