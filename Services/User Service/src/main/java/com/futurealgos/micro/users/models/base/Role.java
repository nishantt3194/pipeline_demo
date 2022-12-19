/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.root.BaseEntity;
import com.futurealgos.micro.users.utils.enums.Permission;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class Role extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3276921390285247051L;

    @Indexed(unique = true)
    private String tag;

    private Permission permission;

    @DocumentReference(lazy = true, collection = "entity_store")
    private EntityStore store;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(tag, role.tag).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(tag).toHashCode();
    }
}
