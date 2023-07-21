package com.futurealgos.admin.dto.response.machine;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record MachineSearch(String identifier,
                            String label,
                            Double speed,
                            Double production,
                            Double tolerance) implements Serializable {
}
