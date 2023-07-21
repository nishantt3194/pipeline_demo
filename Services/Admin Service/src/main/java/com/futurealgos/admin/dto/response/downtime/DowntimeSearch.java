package com.futurealgos.admin.dto.response.downtime;

import lombok.Builder;

@Builder
public record DowntimeSearch(
        String label,
        String identifier
) {
}
