package com.futurealgos.admin.dto.response.product;

import com.futurealgos.admin.dto.response.variant.VariantSearch;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record ProductSearch(
        String label,
        String name,
        String identifier,
        List<VariantSearch> variants

) implements Serializable {

}
