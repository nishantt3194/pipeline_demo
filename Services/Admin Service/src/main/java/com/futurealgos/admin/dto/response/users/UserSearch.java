package com.futurealgos.admin.dto.response.users;


import lombok.Builder;

import java.io.Serializable;

@Builder
public record UserSearch(
        String label,
        String identifier) implements Serializable {


}
