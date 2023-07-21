package com.futurealgos.admin.dto.response.downtime;

import com.futurealgos.admin.models.primary.Category;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record CategoryInfo(
        String identifier,
        String label,
        List<CategoryRequirementInfo> requirements,
        List<CategoryInfo> subcategory
) implements Serializable {

    public static CategoryInfo from(Category category) {
        return CategoryInfo.builder()
                .label(category.getName())
                .identifier(category.getId().toString())
                .requirements(category.getRequirement().stream().map(CategoryRequirementInfo::from).toList())
                .subcategory(category.getSubCategories().stream().map(CategoryInfo::from).toList())
                .build();
    }
}
