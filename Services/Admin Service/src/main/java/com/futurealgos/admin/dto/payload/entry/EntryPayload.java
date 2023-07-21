package com.futurealgos.admin.dto.payload.entry;

import ch.qos.logback.core.boolex.EvaluationException;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.services.area.TemplateService;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
public record EntryPayload(

        String id,
        @NotNull String shift,
        @NotNull String area,
        @NotNull String date,
        @NotNull String product,
        @NotNull String machine,
        String operator,
        @NotNull List<String> variants,
        List<StagedBreakdownPayload> downtimes,
        List<StagedTemplatePayload> quantities,
        String remarks,
        String saveType
) implements Serializable {
    public static EntryPayload from(StagedEntry stagedEntry) {
        return EntryPayload.builder()
                .id(stagedEntry.getId().toString())
                .shift(stagedEntry.getShift().getId().toString())
                .area(stagedEntry.getArea().getId().toString())
                .date(stagedEntry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .product(stagedEntry.getProduct().getId().toString())
                .machine(stagedEntry.getMachine().getId().toString())
                .operator(stagedEntry.getOperator() != null ? stagedEntry.getOperator().getId().toString() : null)
                .variants(stagedEntry.getVariants())
                .downtimes(stagedEntry.getDowntimes().stream().map(StagedBreakdownPayload::from).toList())
                .quantities(stagedEntry.getQuantities().stream().map(StagedTemplatePayload::from).toList())
                .build();
    }

    public static EntryPayload from(Entry entry) {
        List<StagedTemplatePayload> quantities = new ArrayList<>();

        entry.getProductions().stream()
        .filter(production -> production.getTemplate() != null)
        .map(StagedTemplatePayload::from).forEach(quantities::add);

        entry.getRejections().stream()
        .filter(rejection -> rejection.getTemplate() != null)
        .map(StagedTemplatePayload::from).forEach(quantities::add);

        Template totalTemplate = entry.getShift().area.getTemplates().stream().filter(template -> template.getType().equals(Template.Type.TOTAL)).findFirst().orElse(null);
        Double actualValue = entry.getProductions()
                .stream()
                .filter(production -> production.description.equalsIgnoreCase("Total Production"))
                .map(Production::getValue)
                .findFirst().orElse(0.0);
        if(totalTemplate!=null){
            StagedTemplatePayload stagedTemplatePayload = StagedTemplatePayload.builder()
                    .template(totalTemplate.getId().toString())
                    .type(totalTemplate.type.toString())
                    .value(actualValue)
                    .build();

            quantities.add(stagedTemplatePayload);
        }


        return EntryPayload.builder()
                .id(entry.getId().toString())
                .shift(entry.getShift().getId().toString())
                .area(entry.getShift().getArea().getId().toString())
                .date(entry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .product(entry.getProduct().getId().toString())
                .machine(entry.getMachine().getId().toString())
                .operator(entry.getOperator() != null ? entry.getOperator().getId().toString() : null)
                .variants(entry.getVariant().stream().map(variant -> variant.getId().toString()).toList())
                .downtimes(entry.getDowntimes().stream().map(StagedBreakdownPayload::from).toList())
                .quantities(quantities)
                .build();
    }
}
