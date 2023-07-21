package com.futurealgos.admin.dto.response.users;

import com.futurealgos.admin.dto.response.machine.MachineMinimal;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import lombok.Builder;

import java.util.List;

@Builder
public record UserInfo(

        String id,
        String fullName,
        String email,
        String role,
        boolean status,
        boolean assigned,
        List<MachineMinimal> machine

        ) {

    public static UserInfo convert(User user) {
        return UserInfo.builder()
                .fullName(user.getFullName())
                .role(user.getRole().getName())
                .email(user.getEmail())
                .id(user.getId().toString())
                .machine(user.getMachines().stream().map(Machine::minimal).toList())
                .build();
    }


}
