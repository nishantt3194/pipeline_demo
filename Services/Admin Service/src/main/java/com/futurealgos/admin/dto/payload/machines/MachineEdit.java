package com.futurealgos.admin.dto.payload.machines;

import java.util.List;

public record MachineEdit(

        String id ,
        Integer prodLimit,
        Integer tolerance,
        Double speed ,
        Integer lsaTarget ,
        Integer stretchedTarget ,
        Double baseValue ,
        Double oeeTarget
) {

}
