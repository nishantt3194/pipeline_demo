package com.futurealgos.admin.dto.response.downtime;

import lombok.Builder;

@Builder
public record ReasonMinimal(

        String reason,
        String category,
        String type

) {
}
