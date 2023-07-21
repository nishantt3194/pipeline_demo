package com.futurealgos.admin.dto.response.machine;

import com.futurealgos.admin.models.primary.Reason;

public interface ParetoQuery {

    long getDowntime();

    Reason getReason();
}
