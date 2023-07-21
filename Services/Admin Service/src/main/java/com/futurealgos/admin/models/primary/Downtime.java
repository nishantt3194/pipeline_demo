package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.payload.shifts.BreakdownEntry;
import com.futurealgos.admin.dto.response.downtime.DowntimeMinimal;
import com.futurealgos.admin.dto.response.downtime.DowntimeSearch;
import com.futurealgos.admin.exception.exceptions.NotFoundException;
import com.futurealgos.admin.models.secondary.entry.StagedBreakdown;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.models.secondary.reason.CategoryRequirement;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.admin.utils.mappings.DowntimeMap;
import com.futurealgos.admin.utils.mappings.EntryMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = DowntimeMap.TABLE)
@Table(name = DowntimeMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Downtime extends BaseEntity implements Serializable {

    @JsonProperty(DowntimeMap.REASON_TYPE)
    @Column(name = DowntimeMap.REASON_TYPE)
    @Enumerated(value = EnumType.STRING)
    public ReasonType reasonType;

    @JsonProperty(DowntimeMap.REASON)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = DowntimeMap.REASON, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    public Reason reason;

    @JsonProperty(DowntimeMap.CUSTOM_REASON)
    @Column(name = DowntimeMap.CUSTOM_REASON)
    public String customReason;

    @JsonProperty(DowntimeMap.TIME)
    @Column(name = DowntimeMap.TIME)
    public Double time;

    @JsonProperty(DowntimeMap.ASSOCIATION)
    @Column(name = DowntimeMap.ASSOCIATION, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))", nullable = false)
    public UUID association;

    @Enumerated(value = EnumType.STRING)
    @Column(name = DowntimeMap.TYPE)
    @JsonProperty(DowntimeMap.TYPE)
    public BreakdownType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EntryMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    public Entry entry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DowntimeMap.RUNTIME_CATEGORY, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Category runtimeCategory;

    public static Downtime fromEntryShell(BreakdownEntry entry, Machine machine) {
        String reason = entry.reason.equals("OTHER") ? entry.extra : entry.reason;
        if (reason.isBlank())
            reason = "Others";
        UUID association;

        if (entry.type == BreakdownType.STATION_SPECIFIC) {
            Station station = machine.getStations().stream().filter(s -> s.getName().equals(entry.association)).findAny()
                    .orElseThrow(() -> new NotFoundException("Could not find station for breakdown"));

            association = station.getId();
        } else {
            association = machine.getId();
        }

        return Downtime.builder()
                .customReason(reason)
                .time(entry.time)
                .type(entry.type)
                .build();
    }

    public void determineCategory(StagedBreakdown payload) {
        var requirements = reason.getDefaultCategory().getRequirement();
        if (requirements.isEmpty()) {
            setRuntimeCategory(reason.getDefaultCategory());
            return;
        }

        for (CategoryRequirement req : requirements) {
            boolean fulfilled = false;
            switch (req.getType()) {
                case TIME -> {
                    fulfilled = req.getThresholdType()
                            == CategoryRequirement.ThresholdType.MAX && time < req.getValue()
                            || req.getThresholdType()
                            == CategoryRequirement.ThresholdType.MIN && time > req.getValue();
                }
                case MANUAL -> {
                    fulfilled = req.getDerivedCategory().getId().equals(payload.getManualCategory());
                }
            }


            if (fulfilled) {
                setRuntimeCategory(req.getDerivedCategory());
                break;
            }
        }

        if (runtimeCategory == null)
            setRuntimeCategory(reason.getDefaultCategory());

    }

    public void populateBreakdown(BreakdownEntry entry, Machine machine) {
        String reason = entry.reason.equals("OTHER") ? entry.extra : entry.reason;

        UUID association;
        if (entry.type == BreakdownType.STATION_SPECIFIC) {
            Station station = machine.getStations().stream().filter(s -> s.getName().equals(entry.association)).findAny()
                    .orElseThrow(() -> new NotFoundException("Could not find station for breakdown"));
            association = station.getId();
        } else {
            association = machine.getId();
        }

        this.customReason = reason;
        this.time = entry.time;
        this.association = association;
        this.type = entry.type;
    }

    @Override
    public DowntimeMinimal minimal() {
        return DowntimeMinimal.builder()
                .reason(this.reason.getReason())
                .breakdownType(this.type.name())
                .reasonType(this.reasonType.name())
                .runtimeCategory(this.runtimeCategory.getName())
                .customReason(this.customReason)
                .time(time)
                .build();
    }

    @Override
    public DowntimeSearch search() {
        return DowntimeSearch.builder()
                .label(this.reason.getReason())
                .identifier(this.reason.getId().toString())
                .build();
    }

    public enum ReasonType {
        CUSTOM, CONNECTED
    }

}
