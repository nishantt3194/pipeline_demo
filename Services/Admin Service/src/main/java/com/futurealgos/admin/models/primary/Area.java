package com.futurealgos.admin.models.primary;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.area.AreaInfo;
import com.futurealgos.admin.dto.response.area.AreaSearch;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = AreaMap.TABLE)
@Table(name = AreaMap.TABLE, indexes = {
        @Index(name = "idx_area_name_unq", columnList = "name", unique = true),
        @Index(name = "idx_area_id", columnList = "id")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Area extends BaseEntity implements Serializable {


    @JsonProperty(AreaMap.NAME)
    @Column(name = AreaMap.NAME, unique = true)
    private String name;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private Set<Machine> machines;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private Set<Shift> shifts;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private Set<Product> products;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private Set<Template> templates;



    @Override
    public AreaInfo info() {
        return AreaInfo.builder()
                .id(this.id.toString())
                .name(this.name)
                .status(this.status)
                .build();
    }

    @Override
    public AreaSearch search() {
        return AreaSearch.builder()
                .label(getName())
                .identifier(getId().toString()).build();
    }

    @Override
    public AreaInfo minimal() {

        return AreaInfo.builder()
                .id(this.id.toString())
                .name(this.name)
                .status(this.status)
                .machines(machines.size())
                .shifts(shifts.stream().map(Shift::getName).toList())
                .products(products.size())
                .build();
    }
}
