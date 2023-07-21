package com.futurealgos.admin.dto.payload;

import lombok.Builder;

@Builder
public record NewReason(
        String reason,
        String defaultCategory,
        boolean status,
        String type
) {
}
