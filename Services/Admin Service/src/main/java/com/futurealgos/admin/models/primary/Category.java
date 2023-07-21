package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.downtime.CategoryInfo;
import com.futurealgos.admin.models.secondary.reason.CategoryRequirement;
import com.futurealgos.admin.utils.enums.DowntimeCategory;
import com.futurealgos.admin.utils.mappings.CategoryMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity(name = CategoryMap.TABLE)
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Table(name = CategoryMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Category extends BaseEntity implements Serializable {

    @JsonProperty(CategoryMap.NAME)
    @Column(name = CategoryMap.NAME)
    private String name;

    @JsonProperty(CategoryMap.TYPE)
    @Column(name = CategoryMap.TYPE)
    private DowntimeCategory type;

    @ManyToOne
    @JoinColumn(name = CategoryMap.PARENT_CATEGORY, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Category> subCategories;

    @OneToMany(mappedBy = "parentCategory")
    private List<CategoryRequirement> requirement;

    @Override
    public CategoryInfo info() {
        return CategoryInfo.from(this);
    }

    @Override
    public CategoryInfo search() {
        return CategoryInfo.from(this);
    }

}
