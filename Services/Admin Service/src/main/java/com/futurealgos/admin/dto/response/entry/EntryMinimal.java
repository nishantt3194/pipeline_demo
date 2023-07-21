package com.futurealgos.admin.dto.response.entry;


import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record
EntryMinimal(
        String id,
        String date,
        String shift,
        String product,
        String machine,
        Double oee,
        String machineName,
        Double production,
        Double rejection,
        Double goodProduction,
        Double downtime,
        String operator,
        String remarks,

        Double scheduledProductionTime,

        Double rejectionPercentage,

        Double utilizationPercentage,
        Double valueOperatingTime,
        String area,
        Double actualSpeed,
        Double bottleNeckSpeed,
        List<EntryReasons> reasons
        ) {

}
