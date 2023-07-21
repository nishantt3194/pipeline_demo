package com.futurealgos.admin.dto.response.entry;


import lombok.Builder;

@Builder
public record StagedEntryMinimal(
        String machine,
        String area ,
        String product,
        String shift,
        String operator,
        String date
) {
}
