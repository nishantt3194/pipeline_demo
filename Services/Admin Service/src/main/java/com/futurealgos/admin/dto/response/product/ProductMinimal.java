package com.futurealgos.admin.dto.response.product;

import lombok.Builder;

@Builder
public record ProductMinimal(
        String number,
        String description,
        String area,
        Double weight

) {
}
