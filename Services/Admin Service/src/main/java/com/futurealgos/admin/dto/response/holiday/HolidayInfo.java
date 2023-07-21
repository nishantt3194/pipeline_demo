package com.futurealgos.admin.dto.response.holiday;

import lombok.Builder;

@Builder
public record HolidayInfo(
        String name,
        String date
) {
}
