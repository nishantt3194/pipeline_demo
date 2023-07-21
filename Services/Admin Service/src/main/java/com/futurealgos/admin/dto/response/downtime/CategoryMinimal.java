package com.futurealgos.admin.dto.response.downtime;

import lombok.Builder;

@Builder
public record CategoryMinimal(
        String label,
        String identifier
) {
}
