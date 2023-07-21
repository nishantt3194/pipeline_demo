package com.futurealgos.admin.models.secondary.entry;


import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "staged_breakdown")
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class StagedBreakdown extends BaseEntity implements Serializable {


    @ManyToOne
    @JoinColumn(name = "reason", referencedColumnName = BaseMap.ID)
    private Reason reason;

    private Double time;


    @Column(name = "manual_category", columnDefinition = "BINARY(16)")
    private UUID manualCategory;

    @Column(columnDefinition = "BINARY(16)")
    private UUID association;

    private BreakdownType type;
    private String extra;


    @ManyToOne
    @JoinColumn(name = "entry", referencedColumnName = BaseMap.ID)
    private StagedEntry entry;

    @Override
    public String toString() {
        return "StagedBreakdown{" +
                "reason=" + reason +
                ", time=" + time +
                ", manualCategory=" + manualCategory +
                ", association=" + association +
                ", type=" + type +
                ", extra='" + extra + '\'' +
                ", entry=" + entry +
                '}';
    }
}
