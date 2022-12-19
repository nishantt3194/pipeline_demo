/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.base;

import com.futurealgos.micro.auth.models.root.BaseEntity;
import com.futurealgos.micro.auth.utils.enums.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "roles")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3276921390285247051L;

    private String tag;

    private Permission permission;

    @DocumentReference(lazy = true, collection = "entity_store")
    private EntityStore store;

    private String description;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tag", tag)
                .append("permission", permission)
                .append("store", store)
                .append("description", description)
                .append("id", id)
                .toString();
    }


}
