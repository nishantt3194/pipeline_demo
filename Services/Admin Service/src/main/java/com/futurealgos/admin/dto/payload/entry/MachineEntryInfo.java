package com.futurealgos.admin.dto.payload.entry;


import com.futurealgos.admin.models.primary.Entry;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record MachineEntryInfo(


        String machine,
        Double oee ,
        Double production,
        Double downtime,
        Double rejection,

        Double utilizationLoss,

        Double valurOperatingTime,

        Double utilizationPercentage,

        Double rejectionPercentage,

        String shiftName ,
        String shiftDate ,

        String area



) {

    public static MachineEntryInfo convert(Entry entry ){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MMM-yyy");
        return MachineEntryInfo.builder()
                .machine(entry.getMachine().getName())
                .downtime(entry.getTotalDowntime())
                .production(entry.getGoodProduction())
                .rejection(entry.getTotalRejection())
                .oee(Math.round(entry.getOee()*100)/100.0)
                .utilizationLoss(entry.utilizationLoss())
                .area(entry.getMachine().getArea().getName())
                .rejectionPercentage(entry.getRejectionPercent())
                .utilizationPercentage(entry.utilizationPercentage())
                .shiftDate(df.format(entry.getShiftDate()))
                .shiftName(entry.getShift().getName())
                .valurOperatingTime(entry.valueOperatingTime())
                .build();
    }
}
