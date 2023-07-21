package com.futurealgos.admin.dto.response.downtime;

import com.futurealgos.admin.models.secondary.reason.CategoryRequirement;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record CategoryRequirementInfo(

        String prompt,
        String parentCategory,
        CategoryRequirement.Type type,
        CategoryRequirement.ThresholdType thresholdType,
        Double value,
        String derivedCategory
) implements Serializable {

    public static CategoryRequirementInfo from(CategoryRequirement requirement) {
        return CategoryRequirementInfo.builder()
                .prompt(requirement.getPrompt())
                .parentCategory(requirement.getParentCategory().getId().toString())
                .type(requirement.getType())
                .thresholdType(requirement.getThresholdType())
                .value(requirement.getValue())
                .derivedCategory(requirement.getDerivedCategory().getId().toString())
                .build();
    }
}
