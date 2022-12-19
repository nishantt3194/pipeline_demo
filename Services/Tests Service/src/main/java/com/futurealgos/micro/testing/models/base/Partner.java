/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
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

    public enum ACLType {
        WHITELISTED, BLACKLISTED
    }

    @Field("status")
    private Status status;
    @Field("name")
    private String name;
    @Field("acl_type")
    public ACLType aclType;

    @Field("acl_status")
    public boolean aclStatus;

    @Field("acl_tests")
    @DocumentReference(collection = "test", lazy = true)
    private Set<Test> aclTests;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("status", status)
                .append("name", name)
                .append("aclType", aclType)
                .append("aclStatus", aclStatus)
                .append("aclTests", aclTests)
                .toString();
    }
}
