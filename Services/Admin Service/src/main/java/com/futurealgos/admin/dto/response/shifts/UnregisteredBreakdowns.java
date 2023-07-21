package com.futurealgos.admin.dto.response.shifts;


import com.futurealgos.admin.utils.enums.BreakdownType;

import java.util.UUID;

public interface UnregisteredBreakdowns {
    UUID getAssociation();

    String getReason();

    BreakdownType getType();

    long getOccurrence();


}
