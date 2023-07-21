package com.futurealgos.admin.models.secondary.machine;

import com.futurealgos.admin.models.primary.Machine;
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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class MachineSpecificRequirement extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "machine", referencedColumnName = BaseMap.ID)
    private Machine machine;


}
