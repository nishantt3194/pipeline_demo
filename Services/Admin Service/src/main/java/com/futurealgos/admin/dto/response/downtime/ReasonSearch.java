package com.futurealgos.admin.dto.response.downtime;

import java.util.stream.Collectors;

import com.futurealgos.admin.models.primary.Reason;
import lombok.Builder;

@Builder
public record ReasonSearch(
        String label,
        String category,
        String type,
        boolean status,
        String identifier,
        String association,
        String categoryLabel
) {
}
