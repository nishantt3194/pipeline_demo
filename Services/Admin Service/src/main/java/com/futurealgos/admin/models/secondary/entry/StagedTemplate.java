package com.futurealgos.admin.models.secondary.entry;

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
@Entity(name = "staged_template")
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class StagedTemplate extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "template", referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    public Template template;

    public Template.Type type;

    public Double value;

    @Column(name = "actual_value")
    public Double actualValue;
    @ManyToOne
    @JoinColumn(name = "entry", referencedColumnName = BaseMap.ID)
    private StagedEntry entry;

    @Override
    public String toString() {
        return "StagedTemplate{" +
                "template=" + template +
                ", type=" + type +
                ", value=" + value +
                ", actualValue=" + actualValue +
                ", entry=" + entry +
                ", id=" + id +
                '}';
    }
}
