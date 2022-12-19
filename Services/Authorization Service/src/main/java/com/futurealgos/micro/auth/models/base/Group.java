/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.base;

import com.futurealgos.micro.auth.models.main.AuthDirectory;
import com.futurealgos.micro.auth.models.root.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "groups")
public class Group extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3578224856280474487L;

    @Field(name = "group_name")
    private String groupName;

    @Field(name = "description")
    private String description;

    @DocumentReference(collection = "auth_directory")
    private AuthDirectory directory;

    @DocumentReference(lazy = true, collection = "roles")
    private List<Role> roles;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupName", groupName)
                .append("description", description)
                .append("directory", directory)
                .append("roles", roles)
                .toString();
    }
}
