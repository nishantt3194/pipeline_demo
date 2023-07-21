package com.futurealgos.admin.dto.response.entry;

import com.futurealgos.admin.dto.payload.entry.StagedBreakdownPayload;
import com.futurealgos.admin.dto.payload.entry.StagedTemplatePayload;
import com.futurealgos.admin.models.secondary.entry.StagedEntry;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record StagedEntryInfo(
        String area,
        String shift,

        String machine,

        String product,

        String operator,

        List<String > variant,

        List<StagedTemplatePayload> quantities ,

        List<StagedBreakdownPayload> downtimes,
        String date,
        String remarks
) {
    public static StagedEntryInfo convert(StagedEntry entry ){
        return  StagedEntryInfo.builder()
                .area(entry.area.getName())
                .machine(entry.machine.getName())
                .date(entry.shiftDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-ddd")))
                .shift(entry.shift.name)
                .operator(entry.getOperator().getFullName())
                .product(entry.product.getCatalogNum())
                .downtimes(entry.downtimes.stream().map(d-> StagedBreakdownPayload.builder()
                        .reason(d.getReason().getReason())
                        .type(d.getType().name())
                        .association(d.getAssociation().toString())
                        .manualCategory(d.getManualCategory().toString())
                        .time(d.getTime())
                        .build()).toList())
                .quantities(entry.quantities.stream().map(q->StagedTemplatePayload.builder()
                        .value(q.value)
                        .type(q.type.name())
                        .build()).toList())
                .remarks(entry.remarks)
                .variant(entry.getVariants())
                .build();
    }
}
