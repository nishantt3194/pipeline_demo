package com.futurealgos.admin.dto.payload.machines;

import java.util.List;

public record NewMachine(
        String name,
        String area,
        double speed,
        int prodLimit,
        int tolerance,
        int lsa,
        int stretched,

        List<String> stations


) {


}
