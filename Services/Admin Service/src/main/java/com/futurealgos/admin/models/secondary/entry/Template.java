package com.futurealgos.admin.models.secondary.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.utils.enums.Sign;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.admin.utils.mappings.FormulaMap;
import com.futurealgos.admin.utils.mappings.TemplateMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = TemplateMap.TABLE)
@Table(name = TemplateMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Template extends BaseEntity implements Serializable {

    @JsonProperty(TemplateMap.TYPE)
    @Enumerated(value = EnumType.STRING)
    @Column(name = TemplateMap.TYPE)
    public Type type;
    @JsonProperty(TemplateMap.SIGN)
    @Enumerated(value = EnumType.STRING)
    @Column(name = TemplateMap.SIGN)
    public Sign sign;

    @JsonProperty(TemplateMap.DESCRIPTION)
    @Column(name = TemplateMap.DESCRIPTION)
    public String description;
    @ManyToOne
    @JoinColumn(name = FormulaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Formula formula;
    @ManyToOne
    @JoinColumn(name = AreaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Area area;

    public enum Type {
        PRODUCTION, REJECTION, TOTAL, EXTRA
    }

    @Override
    public String toString() {
        return "Template{" +
                "type=" + type +
                ", sign=" + sign +
                ", description='" + description + '\'' +
                ", formula=" + formula +
                ", area=" + area +
                ", id=" + id +
                '}';
    }
}
