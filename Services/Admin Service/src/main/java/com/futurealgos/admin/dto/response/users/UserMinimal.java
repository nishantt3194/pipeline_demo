package com.futurealgos.admin.dto.response.users;

import lombok.Builder;

@Builder
public record UserMinimal(
        String id,
        String firstName,
        String lastName,
        String role,
        String email,
        boolean status


) {


}
