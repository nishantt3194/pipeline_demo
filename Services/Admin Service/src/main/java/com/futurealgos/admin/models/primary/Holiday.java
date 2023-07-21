package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.holiday.HolidayInfo;
import com.futurealgos.admin.utils.mappings.HolidayMap;
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


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = HolidayMap.TABLE)
@Table(indexes = {
        @Index(name = "idx_holiday_name_date", columnList = "name, date")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Holiday extends BaseEntity implements Serializable {

    @JsonProperty(HolidayMap.NAME)
    @Column(name = HolidayMap.NAME)
    private String name;

    @JsonProperty(HolidayMap.DESCRIPTION)
    @Column(name = HolidayMap.DESCRIPTION)
    private String description;

    @JsonProperty(HolidayMap.DATE)
    @Column(name = HolidayMap.DATE, columnDefinition = "DATE")
    private LocalDate date;

    @Override
    public HolidayInfo info(){
        return HolidayInfo.builder()
                .date(String.valueOf(this.date))
                .name(this.name)
                .build();
    }

    @Override
    public HolidayInfo minimal(){
        return HolidayInfo.builder()
                .name(this.name)
                .date(String.valueOf(this.date))
                .build();
    }

}
