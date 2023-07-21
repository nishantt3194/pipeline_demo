package com.futurealgos.admin.dto.payload.template;

import com.futurealgos.admin.models.secondary.entry.StagedTemplate;
import com.futurealgos.admin.models.secondary.entry.Template;
import lombok.Builder;

@Builder
public class TemplatePayload {

    public String id;
    public Template.Type type;
    public Double value;

    public TemplatePayload() {
    }

    public TemplatePayload(String id, Template.Type type, Double value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public static TemplatePayload fromCached(StagedTemplate cached) {
        return new TemplatePayload(
                cached.getId().toString(),
                cached.type,
                cached.value
        );
    }

    @Override
    public String toString() {
        return "TemplatePayload{" +
                "id=" + id +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
