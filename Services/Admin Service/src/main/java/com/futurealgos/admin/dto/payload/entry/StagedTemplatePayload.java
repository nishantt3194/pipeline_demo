package com.futurealgos.admin.dto.payload.entry;


import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.Rejection;
import com.futurealgos.admin.models.secondary.entry.StagedTemplate;
import lombok.Builder;

@Builder
public record StagedTemplatePayload(
        String template,
        String type,
        Double value
) {
    public static StagedTemplatePayload from(StagedTemplate stagedTemplate) {
        return StagedTemplatePayload.builder()
                .template(stagedTemplate.getTemplate().getId().toString())
                .type(stagedTemplate.getType().toString())
                .value(stagedTemplate.getValue())
                .build();
    }

    public static StagedTemplatePayload from(Production production) {
        return StagedTemplatePayload.builder()
                .template(production.getTemplate().getId().toString())
                .type(production.getTemplate().getType().toString())
                .value(production.getActualValue())
                .build();
    }

    public static StagedTemplatePayload from(Rejection rejection) {
        return StagedTemplatePayload.builder()
                .template(rejection.getTemplate().getId().toString())
                .type(rejection.getTemplate().getType().toString())
                .value(rejection.getActualValue())
                .build();
        
    }
}
