package com.futurealgos.admin.dto.response.machine;

import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.precheck.PrecheckInfo;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.secondary.machine.Precheck;
import lombok.Builder;

import java.util.List;

@Builder
public record MachineInfo(
        String id,
        String name,
        boolean status,
        int stretchedTarget,
        int lsaTarget,
        int prodLimit,
        Double speed,
        int tolerance,
        String area,
        Double oeeTarget,
        Double baseValue,
        List<StationInfo> stations,
        List<EntryMinimal> shifts,
        List<PrecheckInfo> precheck
) {
}
