package com.futurealgos.admin.dto.payload.users;


import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.utils.enums.Role;

import java.util.UUID;

public record UpdateUser (
        String id ,
        String role,
        boolean status

){


    public User populate(User users) {
        return users;
    }


}
