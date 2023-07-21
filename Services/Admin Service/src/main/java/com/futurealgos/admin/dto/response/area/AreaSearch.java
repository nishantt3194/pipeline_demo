package com.futurealgos.admin.dto.response.area;

import lombok.Builder;

@Builder
public record AreaSearch(
        String label,
        String identifier) {


}
