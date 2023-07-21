package com.futurealgos.admin.models.secondary.machine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.machine.StationInfo;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.admin.utils.mappings.StationMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import com.futurealgos.specs.objects.DefaultSearchResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = StationMap.TABLE)
@Table(name = StationMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Station extends BaseEntity implements Serializable {

    @JsonProperty(StationMap.NAME)
    @Column(name = StationMap.NAME, nullable = false, updatable = false)
    public String name;

    @ManyToOne
    @JoinColumn(name = MachineMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Machine machine;


    @Override
    public DefaultSearchResponse search() {
        return DefaultSearchResponse.builder()
                .label(this.name)
                .identifier(this.id.toString())
                .build();
    }

    @Override
    public StationInfo info(){
        return  StationInfo.builder()
                .id(this.id.toString())
                .name(this.name)
                .build();
    }
}
