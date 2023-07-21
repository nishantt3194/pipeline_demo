package com.futurealgos.admin.dto.payload.area;

import com.futurealgos.admin.models.primary.Area;

public record NewAreaPayload(
        String name,

        boolean status
) {

    public Area convert() {
        return Area.builder()
                .name(name)
                .build();
    }
}
