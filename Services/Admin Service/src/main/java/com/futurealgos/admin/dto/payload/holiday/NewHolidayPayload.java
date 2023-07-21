package com.futurealgos.admin.dto.payload.holiday;

import com.futurealgos.admin.models.primary.Holiday;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record NewHolidayPayload(
        String name,
        String description,
        String date
) {


 public Holiday convert(){
     return Holiday.builder()
             .name(name)
             .description(description)
             .date(LocalDate.parse(date))
             .build();
 }

}
