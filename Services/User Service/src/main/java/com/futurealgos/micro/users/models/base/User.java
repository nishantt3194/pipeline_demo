/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.embedded.Address;
import com.futurealgos.micro.users.models.root.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4422508847654421636L;

    @Field("first_name")
    private String firstName;
    
    @Field("last_name")
    private String lastName;

    @Field("first_time_login")
    private boolean firstTimeLogin;

    private Address address;

    @Field
    @Indexed(unique = true)
    private String username;

    @Field
    private boolean status;

    @Field
    private String password;

    @Field("partner")
    @DocumentReference(collection = "partners", lazy = true)
    private Partner partner;

    @DocumentReference(collection = "auth_directory")
    private AuthDirectory directory;

    @Field("primary_group")
    @DocumentReference(lazy = true, collection = "groups")
    private Group primaryGroup;

    @DocumentReference(lazy = true, collection = "role")
    private List<Role> role;

    @DocumentReference(lazy = true, collection = "groups")
    private List<Group> group;

    public String getFullName() {
        if(firstName == null) return "";
        if(lastName == null) return firstName;
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
