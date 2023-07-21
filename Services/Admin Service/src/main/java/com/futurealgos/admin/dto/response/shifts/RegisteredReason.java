package com.futurealgos.admin.dto.response.shifts;

import com.futurealgos.admin.models.primary.Reason;
import com.futurealgos.admin.utils.enums.BreakdownType;

import java.io.Serializable;

public class RegisteredReason implements Serializable {
    private static final long serialVersionUID = -6116331368086989901L;
    public String id;
    public String type;
    public boolean status;
    public String reason;

    public RegisteredReason(String id, String type, boolean status, String reason) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.reason = reason;
    }

    public static RegisteredReason fromReason(Reason reason) {
        if (reason.getType().equals(BreakdownType.STATION_SPECIFIC))
            return new RegisteredReason(
                    reason.getId().toString(),
                    reason.getType().toString(),
                    reason.getStatus(),
                    reason.getReason()
            );

        return new RegisteredReason(
                reason.getId().toString(),
                reason.getType().toString(),
                reason.getStatus(),
                reason.getReason()
        );
    }
}
