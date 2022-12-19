/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.root.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@Document(collection = "pre_registered_users")
public class PreRegister extends BaseEntity {

    @Field
    private String name;

    @Field
    private String email;

    @Field
    private String country;

    @Field("phone_number")
    private String phoneNo;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .append("country", country)
                .append("phoneNo", phoneNo)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .append("createdBy", createdBy)
                .append("updatedBy", updatedBy)
                .append("version", version)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PreRegister that = (PreRegister) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(name, that.name).append(email, that.email).append(country, that.country).append(phoneNo, that.phoneNo).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(email).toHashCode();
    }
}
