package com.futurealgos.admin.dto.response.downtime;

public record NewStationSpecificReason(
        String machine,
        String station,
        String reason
) {
}
