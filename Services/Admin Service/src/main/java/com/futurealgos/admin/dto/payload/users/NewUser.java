package com.futurealgos.admin.dto.payload.users;

import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.utils.enums.Role;

public record NewUser(
        String firstName,
        String lastName,
        String email,
        String gid,
        String role
) {

    public User convert() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .gid(gid)
                .role(Role.valueOf(role))
                .build();
    }
}

