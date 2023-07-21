package com.futurealgos.admin.models.secondary.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.utils.enums.Sign;
import com.futurealgos.admin.utils.mappings.EntryMap;
import com.futurealgos.admin.utils.mappings.ProductionMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Entity(name = ProductionMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Production extends BaseEntity implements Serializable {

    @JsonProperty(ProductionMap.SIGN)
    @Enumerated(value = EnumType.STRING)
    @Column(name = ProductionMap.SIGN)
    public Sign sign;

    @JsonProperty(ProductionMap.DESCRIPTION)
    @Column(name = ProductionMap.DESCRIPTION)
    public String description;

    @JsonProperty(ProductionMap.VALUE)
    @Column(name = ProductionMap.VALUE)
    public Double value;

    @JsonProperty(ProductionMap.ACTUAL_VALUE)
    @Column(name = ProductionMap.ACTUAL_VALUE)
    public Double actualValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntryMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Entry entry;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Template template;
}
