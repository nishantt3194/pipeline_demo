package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.shifts.ShiftInfo;
import com.futurealgos.admin.dto.response.shifts.ShiftMinimal;
import com.futurealgos.admin.dto.response.shifts.ShiftSearch;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.admin.utils.mappings.ShiftMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Getter
@SuperBuilder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = ShiftMap.TABLE)
@Table(indexes = {
        @Index(name = "idx_shift_id_area_name", columnList = "id, area, name"),
        @Index(name = "idx_shift_area", columnList = "area"),
        @Index(name = "idx_shift_start_time", columnList = "start_time"),
        @Index(name = "idx_shift_start_end", columnList = "start_time, stop_time")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Shift extends BaseEntity implements Serializable {


    @JsonProperty(ShiftMap.NAME)
    @Column(name = ShiftMap.NAME, nullable = false, updatable = false)
    public String name;

    @JsonProperty(ShiftMap.SPANS_TO_NEXT_DAY)
    @Column(name = ShiftMap.SPANS_TO_NEXT_DAY)
    public boolean spanToNextDay;

    @JsonProperty(ShiftMap.START_TIME)
    @Column(name = ShiftMap.START_TIME, columnDefinition = "TIME")
    public LocalTime startTime;

    @JsonProperty(ShiftMap.STOP_TIME)
    @Column(name = ShiftMap.STOP_TIME, columnDefinition = "TIME")
    public LocalTime stopTime;

    @ManyToOne
    @JoinColumn(name = AreaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    public Area area;


    public Long getTotalTime() {
        var startDate = LocalDate.now().atTime(startTime);
        var stopDate = LocalDate.now().plusDays(spanToNextDay ? 1: 0).atTime(stopTime);
        return startDate.until(stopDate, ChronoUnit.MINUTES);
    }

    @Override
    public ShiftInfo info() {
        return ShiftInfo.builder()
                .id(id.toString())
                .area(area.getName())
                .name(name)
                .startTime(startTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
                .stopTime(stopTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
                .build();
    }

    @Override
    public ShiftMinimal minimal() {
        return ShiftMinimal.builder()
                .id(id.toString())
                .name(name)
                .area(area.getName())
                .startTime(startTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
                .stopTime(stopTime.format(DateTimeFormatter.ofPattern("hh:mm a")))
                .build();
    }

    @Override
    public ShiftSearch search() {
        return ShiftSearch.builder()
                .identifier(id.toString())
                .label(name)
                .build();
    }
}
