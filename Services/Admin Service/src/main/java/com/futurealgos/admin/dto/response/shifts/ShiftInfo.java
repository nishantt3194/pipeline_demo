package com.futurealgos.admin.dto.response.shifts;

import lombok.Builder;

@Builder
public record ShiftInfo(

        String id,
        String name,
        String startTime,

        String stopTime,

        String area
) {
}
