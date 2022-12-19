/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRole {
    @NotNull
    @NotBlank
    public final String username;

    @NotNull
    public final List<String> role;

    public UserRole(String username, List<String> role) {
        this.username = username;
        this.role = role;
    }
}
