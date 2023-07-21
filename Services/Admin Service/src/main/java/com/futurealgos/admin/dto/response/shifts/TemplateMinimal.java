package com.futurealgos.admin.dto.response.shifts;


import com.futurealgos.admin.models.secondary.entry.Formula;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.utils.enums.Sign;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record TemplateMinimal(
        String id,
        Sign sign,
        String description,
        Boolean toConvert,
        Formula.Operation operation,
        Boolean inKg,
        Double operand,
        Template.Type templateType,

        Formula.Type type
) implements Serializable {
}
