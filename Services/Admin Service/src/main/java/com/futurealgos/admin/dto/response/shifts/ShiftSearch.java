package com.futurealgos.admin.dto.response.shifts;

import lombok.Builder;

@Builder
public record ShiftSearch(

        String label,
        String identifier
) {
}
