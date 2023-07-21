package com.futurealgos.admin.dto.payload.users;

import java.util.List;

public record AssignMachine(
        List<String> userIds,
        List<String> machineIds,
        String assign
) {
}
