package com.futurealgos.admin.dto.payload.entry;


import com.futurealgos.admin.dto.payload.shifts.BreakdownEntry;
import com.futurealgos.admin.dto.payload.template.TemplatePayload;
import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.secondary.entry.Formula;
import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.Rejection;
import com.futurealgos.admin.models.secondary.entry.Template;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.utils.enums.BreakdownType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record EntryEdit(
        String id,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
        String name,


        @NotNull
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z ]*$")
        String area,

        @NotNull
        @NotBlank
        String date,

        @NotNull
        @NotBlank
        String product,

        @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
        String remarks,

        @NotNull
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9- ]*$")
        String machine,

        @NotNull
        @Min(value = 0)
        Double production,

        @NotNull
        @Min(value = 0)
        Double rejection,


        List<BreakdownEntry> downtimes,

        List<TemplatePayload> quantities,

        String operator,

        boolean templateChanged
) {

    public static EntryEdit buildEntryEdit(Entry entry, List<Template> templates) {
        List<BreakdownEntry> breakdowns = new ArrayList<>();
        List<TemplatePayload> quantities = new ArrayList<>();
        for (Downtime breakdown : entry.getDowntimes()) {
            BreakdownEntry payload = new BreakdownEntry();
            payload.reason = breakdown.getReason().getReason();
            payload.time = breakdown.getTime();
            payload.type = breakdown.getType();
            if (breakdown.getType().equals(BreakdownType.STATION_SPECIFIC)) {
                payload.association = entry.getMachine().getStations().stream()
                        .filter(s -> s.getId().equals(breakdown.association))
                        .map(Station::getName)
                        .collect(toSingleton());
            } else {
                payload.association = entry.getMachine().getName();
            }

            breakdowns.add(payload);
        }

        Map<String, Template> temp = new HashMap<>();

        for (Template template : templates) {
            String desc = template.description.replace("(KG)", "").replace("(Kg)", "").trim();
            temp.put(desc, template);
        }

        List<Production> productions = entry.getProductions().stream().filter(p -> temp.containsKey(p.description)).collect(Collectors.toList());
        List<Rejection> rejections = entry.getRejections().stream().filter(p -> temp.containsKey(p.description)).collect(Collectors.toList());

        boolean templateChanged = !(productions.size() + rejections.size() == templates.size());


        if (!templateChanged) {
            for (Production q : productions) {
                Template template = temp.get(q.description);
                TemplatePayload payload = TemplatePayload.builder()
                        .id(template.getId().toString())
                        .type(Template.Type.PRODUCTION)
                        .value(q.getValue())
                        .build();
                Double finalValue = q.getValue();

                if (template.formula != null) {
                    if (template.formula.getType().equals(Formula.Type.EXPRESSION)) {
                        finalValue = template.formula.getOperation().equals(Formula.Operation.DIVIDE) ? finalValue * entry.getProduct().getWeight() / 1000
                                : finalValue / (entry.getProduct().getWeight() * 1000);

                    } else {
                        finalValue = template.formula.getOperation().equals(Formula.Operation.DIVIDE) ? finalValue * template.formula.getOperand() / 1000
                                : finalValue / (template.formula.getOperand() * 1000);

                    }
                }

                payload.value = finalValue;
                quantities.add(payload);

            }

            for (Rejection q : rejections) {
                Template template = temp.get(q.description);//
                TemplatePayload payload = TemplatePayload.builder()
                        .id(template.getId().toString())
                        .type(Template.Type.PRODUCTION)
                        .value(q.getValue())
                        .build();
                Double finalValue = q.getValue();


                if (template.formula != null) {
                    if (template.formula.getType().equals(Formula.Type.EXPRESSION)) {
                        finalValue = template.formula.getOperation().equals(Formula.Operation.DIVIDE) ? finalValue * entry.getProduct().getWeight() / 1000
                                : finalValue / (entry.getProduct().getWeight() * 1000);

                    } else {
                        finalValue = template.formula.getOperation().equals(Formula.Operation.DIVIDE) ? finalValue * template.formula.getOperand() / 1000
                                : finalValue / (template.formula.getOperand() * 1000);

                    }
                }

                finalValue = Math.round(finalValue * 100.0) / 100.0;
                payload.value = finalValue;
                quantities.add(payload);
            }
        }

        return new EntryEdit(
                entry.getId().toString(),
                entry.getShift().getName(),
                entry.getShift().getArea().getName(),
                entry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                entry.getProduct().getCatalogNum() + "-" + entry.getProduct().getDescription(),
                entry.getRemarks(),
                entry.getMachine().getName(),
                0.0,
                0.0,
                breakdowns,
                quantities,
                entry.getOperator() != null ? entry.getOperator().getEmail() : null,
                templateChanged
        );

    }


    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }


    @Override
    public String toString() {
        return "EntryEdit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", date='" + date + '\'' +
                ", product='" + product + '\'' +
                ", remarks='" + remarks + '\'' +
                ", machine='" + machine + '\'' +
                ", production=" + production +
                ", rejection=" + rejection +
                ", downtimes=" + downtimes +
                ", quantities=" + quantities +
                ", templateChanged=" + templateChanged +
                '}';
    }
}
