package com.futurealgos.admin.models.secondary.machine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.precheck.PrecheckInfo;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.admin.utils.mappings.PrecheckMap;
import com.futurealgos.admin.utils.mappings.ReasonMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity(name = PrecheckMap.TABLE)
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Precheck extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = MachineMap.TABLE)
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = ReasonMap.TABLE, referencedColumnName = BaseMap.ID)
    private Reason reason;

    @JsonProperty(PrecheckMap.TIME)
    @Column(name = PrecheckMap.TIME)
    private Double time;

    @Override
    public PrecheckInfo info() {
        return PrecheckInfo.builder()
                .machine(machine.getName())
                .reason(reason.getReason())
                .reasonId(reason.getId().toString())
                .time(time)
                .build();
    }

    @Override
    public PrecheckInfo search() {
        return PrecheckInfo.builder()
                .id(this.id.toString())
                .reason(this.reason.getReason())
                .reasonId(reason.getId().toString())
                .machine(this.machine.getName())
                .time(this.time)
                .build();
    }
}
