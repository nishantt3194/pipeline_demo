package com.futurealgos.admin.dto.response.machine;


import lombok.Builder;

@Builder
public record MachineMinimal(
        String id,
        String name,
        boolean status,
        String area,
        Integer totalStation) {

}

