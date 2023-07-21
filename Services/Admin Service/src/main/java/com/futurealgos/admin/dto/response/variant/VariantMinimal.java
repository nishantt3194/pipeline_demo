package com.futurealgos.admin.dto.response.variant;


import lombok.Builder;

@Builder
public record VariantMinimal(

        String name,
        String description,
        String product
) {


}
