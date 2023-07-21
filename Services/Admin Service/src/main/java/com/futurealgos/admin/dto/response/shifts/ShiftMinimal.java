package com.futurealgos.admin.dto.response.shifts;

import lombok.Builder;

@Builder
public record ShiftMinimal(
        String id,
        String name,
        String area,
        String startTime,
        String stopTime
) {
}
