package com.futurealgos.admin.dto.response.precheck;

import lombok.Builder;

@Builder
public record PrecheckInfo(
        String id,
        String machine,
        String reason,

        String reasonId,
        Double time
) {
}
