package com.futurealgos.admin.dto.payload.precheck;

import com.futurealgos.admin.models.secondary.machine.Precheck;

public record NewPrecheck(

        String id,
        String machine,
        String reason,
        Double time
) {
    public Precheck populate(Precheck precheck) {
        return precheck;
    }
}
