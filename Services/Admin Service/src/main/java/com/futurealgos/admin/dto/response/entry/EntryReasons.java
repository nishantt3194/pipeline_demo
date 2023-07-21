package com.futurealgos.admin.dto.response.entry;

import lombok.Builder;

@Builder
public record EntryReasons(
        String reason ,
        double time
) {
}
