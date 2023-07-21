package com.futurealgos.admin.models.secondary.reason;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.models.primary.Category;
import com.futurealgos.admin.utils.mappings.CategoryRequirementMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class CategoryRequirement extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = CategoryRequirementMap.PARENT_CATEGORY, referencedColumnName = BaseMap.ID)
    private Category parentCategory;
    @JsonProperty(CategoryRequirementMap.TYPE)
    @Column(name = CategoryRequirementMap.TYPE)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @JsonProperty(CategoryRequirementMap.PROMPT)
    @Column(name = CategoryRequirementMap.PROMPT, columnDefinition = "VARCHAR(200)")
    private String prompt;
    @JsonProperty(CategoryRequirementMap.THRESHOLD_TYPE)
    @Column(name = CategoryRequirementMap.THRESHOLD_TYPE)
    @Enumerated(value = EnumType.STRING)
    private ThresholdType thresholdType;
    @JsonProperty(CategoryRequirementMap.VALUE)
    @Column(name = CategoryRequirementMap.VALUE)
    private Double value;
    @ManyToOne
    @JoinColumn(name = CategoryRequirementMap.DERIVED_CATEGORY, referencedColumnName = BaseMap.ID)
    private Category derivedCategory;

    public enum ThresholdType {
        MIN, MAX, DEFAULT
    }

    public enum Type {
        MANUAL, TIME
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("parentCategory", parentCategory.getName())
                .append("type", type)
                .append("prompt", prompt)
                .append("thresholdType", thresholdType)
                .append("value", value)
                .append("derivedCategory", derivedCategory.getName())
                .append("id", id)
                .append("status", status)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .append("deletedOn", deletedOn)
                .append("createdBy", createdBy)
                .append("updatedBy", updatedBy)
                .append("deletedBy", deletedBy)
                .append("version", version)
                .toString();
    }
}
