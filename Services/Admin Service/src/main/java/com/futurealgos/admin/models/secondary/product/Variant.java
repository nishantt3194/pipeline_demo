package com.futurealgos.admin.models.secondary.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.variant.VariantMinimal;
import com.futurealgos.admin.dto.response.variant.VariantSearch;
import com.futurealgos.admin.models.primary.Product;
import com.futurealgos.admin.utils.mappings.ProductMap;
import com.futurealgos.admin.utils.mappings.VariantMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = VariantMap.TABLE)
@Table(name = VariantMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Variant extends BaseEntity implements Serializable {


    @JsonProperty(VariantMap.NAME)
    @Column(name = VariantMap.NAME)
    private String name;

    @JsonProperty(VariantMap.DESCRIPTION)
    @Column(name = VariantMap.DESCRIPTION)
    private String description;

    @ManyToOne
    @JoinColumn(name = ProductMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Product product;

    @Override
    public VariantMinimal info() {
        return VariantMinimal.builder()
                .name(name)
                .description(description)
                .product(product.getCatalogNum())
                .build();
    }

    @Override
    public VariantMinimal minimal() {
        return VariantMinimal.builder()
                .name(name)
                .description(description)
                .product(product.getCatalogNum())
                .build();
    }

    @Override
    public VariantSearch search() {
        return VariantSearch.builder()
                .label(name)
                .identifier(id.toString())
                .build();
    }
}
