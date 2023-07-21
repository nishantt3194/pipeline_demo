package com.futurealgos.admin.dto.response.area;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record AreaInfo(
        String id,
        String name,
        boolean status,
        Integer machines,
        Integer products,
        List<String> shifts
) implements Serializable {
}
