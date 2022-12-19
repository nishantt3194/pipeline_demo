/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.root;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    protected ObjectId id;

    @Field("created_on")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected Date createdOn;

    @Field("updated_on")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    protected Date updatedOn;

    @Field("created_by")
    protected String createdBy;

    @Field("updated_by")
    protected String updatedBy;

    @Version
    protected Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return new EqualsBuilder().append(id.toHexString(), that.id.toHexString()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id.toHexString()).toHashCode();
    }

}

