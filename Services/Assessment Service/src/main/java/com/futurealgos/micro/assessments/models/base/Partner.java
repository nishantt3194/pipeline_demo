/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "partners")
public class Partner extends BaseEntity implements Serializable {

    public enum Status {
        PENDING, ACTIVE, INACTIVE;
    }

    @Field("status")
    private Status status;

    @Field("name")
    private String name;

    @Field("assessors")
    @Builder.Default
    @DocumentReference(collection = "users", lazy = true)
    private Set<User> assessors = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("status", status)
                .append("name", name)
                .toString();
    }
}
