package com.futurealgos.admin.dto.response.variant;

import com.futurealgos.admin.models.secondary.product.Variant;
import lombok.Builder;

@Builder
public record VariantSearch(
        String label,
        String identifier

) {
    public static VariantSearch from(Variant variant) {
        return VariantSearch.builder()
                .label(variant.getName())
                .identifier(variant.getId().toString())
                .build();
    }
}
