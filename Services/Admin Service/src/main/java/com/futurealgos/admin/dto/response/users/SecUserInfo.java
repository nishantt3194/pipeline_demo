package com.futurealgos.admin.dto.response.users;

import lombok.Builder;

@Builder
public record SecUserInfo(
        String username,
        String role ,
        String area ,
        String firstName,
        String lastName ,
        String gid
) {
}
