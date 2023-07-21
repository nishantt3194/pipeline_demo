package com.futurealgos.admin.dto.response.users;

import com.futurealgos.admin.models.primary.Entry;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record EntryDetails(
        String machineName,
        String date ,
        String productName,
        String shift ,
        String area
) {

    public static EntryDetails convert (Entry entry ){
        return  EntryDetails.builder()
                .area(entry.getShift().area.getName())
                .shift(entry.getShift().name)
                .productName(entry.getProduct().getCatalogNum())
                .machineName(entry.getMachine().getName())
                .date(entry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")))
                .build();
    }
}
