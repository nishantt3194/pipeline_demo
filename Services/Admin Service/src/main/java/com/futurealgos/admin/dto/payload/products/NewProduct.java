package com.futurealgos.admin.dto.payload.products;

import lombok.Builder;

@Builder

public record NewProduct(
        String number,
        String area,
        String description,
        Double weight,
        String type

) {
}
