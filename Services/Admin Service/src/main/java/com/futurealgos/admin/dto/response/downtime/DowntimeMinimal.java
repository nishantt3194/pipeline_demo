package com.futurealgos.admin.dto.response.downtime;

import lombok.Builder;

@Builder
public record DowntimeMinimal(

        String reasonType,
        String reason,
        String customReason,
        Double time,
        String breakdownType,
        String runtimeCategory
) {
}
