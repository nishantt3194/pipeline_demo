package com.futurealgos.admin.dto.payload.shifts;

import lombok.Builder;

@Builder
public record UpdateReason(
        String id ,
        boolean status
) {
}
