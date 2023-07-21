package com.futurealgos.admin.models.secondary.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.admin.utils.mappings.FormulaMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = FormulaMap.TABLE)
@Table(name = FormulaMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Formula extends BaseEntity {

    @JsonProperty(FormulaMap.IN_KG)
    @Column(name = FormulaMap.IN_KG)
    private boolean inKg;

    @JsonProperty(FormulaMap.OPERAND)
    @Column(name = FormulaMap.OPERAND)
    private Double operand;

    @JsonProperty(FormulaMap.TYPE)
    @Column(name = FormulaMap.TYPE)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @JsonProperty(FormulaMap.OPERATION)
    @Column(name = FormulaMap.OPERATION)
    @Enumerated(value = EnumType.STRING)
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = AreaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    private Area area;

    public enum Type {
        CONSTANT, EXPRESSION
    }

    public enum Operation {
        MULTIPLY, DIVIDE
    }

}
