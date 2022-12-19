/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
public class MinimalUser implements Serializable {


    public String id;

    public String email;
    private String fullName;

    private boolean status;

    public MinimalUser(String id, String email, String fullName, boolean status) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
    }

    public static MinimalUser from(User user) {
        return new MinimalUser(
                user.getId().toHexString(),
                user.getUsername(),
                user.getFirstName() + " " + user.getLastName(),
                user.isStatus()
        );
    }
}
