package com.futurealgos.admin.dto.payload.shifts;

import com.futurealgos.admin.utils.enums.BreakdownType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class BreakdownEntry {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
    public String reason;

    @NotNull
    @Min(value = 0)
    public Double time;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9- ]*$")
    public String association;

    @NotNull
    public BreakdownType type;

    @Pattern(regexp = "^[a-zA-Z0-9 ]*$")
    public String extra;

    public BreakdownEntry() {
    }

    public BreakdownEntry(String reason, Double time, String association, BreakdownType type, String extra) {
        this.reason = reason;
        this.time = time;
        this.association = association;
        this.type = type;
        this.extra = extra;
    }

//    public static BreakdownEntry fromCached(StagedBreakdown cached) {
//        return new BreakdownEntry(
//                cached.reason,
//                cached.time,
//                cached.association,
//                cached.type,
//                cached.extra
//        );
//    }

    @Override
    public String toString() {
        return "BreakdownEntry{" +
                "reason='" + reason + '\'' +
                ", time=" + time +
                ", association='" + association + '\'' +
                ", type=" + type +
                ", extra='" + extra + '\'' +
                '}';
    }

}
