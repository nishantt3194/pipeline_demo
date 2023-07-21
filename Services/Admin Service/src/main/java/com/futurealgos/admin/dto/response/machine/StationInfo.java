package com.futurealgos.admin.dto.response.machine;

import lombok.Builder;

@Builder
public record StationInfo(
        String id ,
        String name
) {


}
