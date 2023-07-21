package com.futurealgos.admin.dto.payload.entry;

import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.models.secondary.entry.StagedBreakdown;
import lombok.Builder;

@Builder
public record StagedBreakdownPayload(
        String reason,
        Double time,
        String manualCategory,
        String association,
        String type
) {
    public static StagedBreakdownPayload from(StagedBreakdown stagedBreakdown) {
        return StagedBreakdownPayload.builder()
                .reason(stagedBreakdown.getReason().getId().toString())
                .time(stagedBreakdown.getTime())
                .manualCategory(stagedBreakdown.getManualCategory() != null ? stagedBreakdown.getManualCategory().toString() : null)
                .association(stagedBreakdown.getAssociation() != null ? stagedBreakdown.getAssociation().toString() : null)
                .type(stagedBreakdown.getType().toString())
                .build();
    }

    public static StagedBreakdownPayload from(Downtime downtime) {
        return StagedBreakdownPayload.builder()
                .reason(downtime.getReason().getId().toString())
                .time(downtime.getTime())
                .manualCategory(downtime.getRuntimeCategory() != null ? downtime.getRuntimeCategory().getId().toString() : null)
                .association(downtime.getAssociation() != null ? downtime.getAssociation().toString() : null)
                .type(downtime.getType().toString())
                .build();
    }
}
