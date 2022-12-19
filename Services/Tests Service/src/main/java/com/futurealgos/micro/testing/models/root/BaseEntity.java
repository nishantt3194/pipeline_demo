/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.root;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.futurealgos.micro.testing.utils.mappings.BaseMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    protected ObjectId id;

    @Field(BaseMap.CREATED_ON)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdOn;

    @Field(BaseMap.UPDATED_ON)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedOn;

    @Field(BaseMap.CREATED_BY)
    private String createdBy;

    @Field(BaseMap.UPDATED_BY)
    private String updatedBy;

    @Version
    private Integer version;

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

