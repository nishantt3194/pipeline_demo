package com.futurealgos.admin.dto.payload.shifts;

public record NewShift(
        String name,
        String startTime,
        String stopTime,
        String area
) {
}
