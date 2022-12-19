/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.model;


import com.futurealgos.micro.credits.utils.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


@Getter
@Setter
@Document(collection = "users")
public class User extends BaseEntity implements Serializable {


    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field
    private String username;

    @Field(name = "password")
    private String password;

    @Field
    private Role role;

    @Field(name = "balance")
    private Double credits;


}
