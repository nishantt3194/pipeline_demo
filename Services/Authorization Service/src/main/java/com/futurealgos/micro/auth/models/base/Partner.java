/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.base;

import com.futurealgos.micro.auth.models.root.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "partner")
public class Partner extends BaseEntity implements Serializable {
    public enum Status {
        PENDING, ACTIVE, INACTIVE;
    }

    @Field
    private String name;

    @Field("status")
    private Status status;

    private Double credits;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("status", status)
                .append("credits", credits)
                .toString();
    }
}
