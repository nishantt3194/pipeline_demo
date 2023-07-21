package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.product.ProductInfo;
import com.futurealgos.admin.dto.response.product.ProductMinimal;
import com.futurealgos.admin.dto.response.product.ProductSearch;
import com.futurealgos.admin.dto.response.variant.VariantSearch;
import com.futurealgos.admin.models.secondary.product.Variant;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.admin.utils.mappings.ProductMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ProductMap.TABLE, indexes = {
        @Index(name = "idx_product_id", columnList = "id"),
        @Index(name = "idx_product_status", columnList = "status"),
        @Index(name = "idx_product_area", columnList = "area")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Product extends BaseEntity implements Serializable {

    @JsonProperty(ProductMap.CATALOG_NUM)
    @Column(name = ProductMap.CATALOG_NUM)
    private String catalogNum;

    @JsonProperty(ProductMap.DESCRIPTION)
    @Column(name = ProductMap.DESCRIPTION)
    private String description;

    @JsonProperty(ProductMap.TYPE)
    @Enumerated(value = EnumType.STRING)
    @Column(name = ProductMap.TYPE)
    private Type type;

    @JsonProperty(ProductMap.WEIGHT)
    @Column(name = ProductMap.WEIGHT)
    private Double weight;

    @ManyToOne
    @JoinColumn(name = AreaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Area area;

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private Set<Variant> variant = new HashSet<>();

    @Override
    public ProductMinimal minimal() {
        return ProductMinimal.builder()
                .area(area.getName())
                .number(catalogNum)
                .description(description)
                .weight(weight)
                .build();
    }

    @Override
    public ProductSearch search() {
        return ProductSearch.builder()
                .label(catalogNum)
                .name(description)
                .identifier(id.toString())
                .variants(variant.stream().map(VariantSearch::from).toList())
                .build();
    }

    @Override
    public ProductInfo info() {
        return ProductInfo.builder()
                .area(area.getName())
                .number(catalogNum)
                .description(description)
                .weight(weight)
                .build();
    }

    public enum Type {
        NORMAL, COMPLEX
    }
}
