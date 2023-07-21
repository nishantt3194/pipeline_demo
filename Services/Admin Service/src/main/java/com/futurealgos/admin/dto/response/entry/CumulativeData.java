package com.futurealgos.admin.dto.response.entry;

import com.futurealgos.admin.models.primary.Entry;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CumulativeData implements Serializable {

    @Builder.Default
    public Double production = 0.0;

    @Builder.Default
    public Double rejection = 0.0;

    @Builder.Default
    public Double oeePercentage = 0.0;

    @Builder.Default
    public Double utilizationPercentage = 0.0;

    public int count;

    public int oeeCount;


    public static CumulativeData from(Entry entry) {
        return CumulativeData.builder()
                .production(entry.getGoodProduction())
                .rejection(entry.getTotalRejection())
                .oeePercentage(entry.getOee())
                .utilizationPercentage(entry.utilizationPercentage())
                .count(0)
                .build();
    }




}
