package com.futurealgos.admin.dto.response.shifts;

public record DowntimeInfo(
        String reason,
        Double time,
        String type,
        String association
) {

}
