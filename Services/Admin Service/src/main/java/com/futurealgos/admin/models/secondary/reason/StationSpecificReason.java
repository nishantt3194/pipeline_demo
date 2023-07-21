package com.futurealgos.admin.models.secondary.reason;

import com.futurealgos.admin.dto.response.downtime.ReasonSearch;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.admin.utils.mappings.ReasonMap;
import com.futurealgos.admin.utils.mappings.StationMap;
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
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "station_specific_reasons")
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class StationSpecificReason extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = MachineMap.TABLE, referencedColumnName = BaseMap.ID)
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = StationMap.TABLE, referencedColumnName = BaseMap.ID)
    private Station station;

    @ManyToOne
    @JoinColumn(name = ReasonMap.TABLE, referencedColumnName = BaseMap.ID)
    private Reason reason;

    @Override
    public ReasonSearch search() {
        return ReasonSearch.builder()
                .label(reason.getReason())
                .identifier(reason.getId().toString())
                .category(reason.getDefaultCategory().getId().toString())
                .association(station.getId().toString())
                .build();
    }
}
