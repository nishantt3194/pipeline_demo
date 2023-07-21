package com.futurealgos.admin.dto.response.shifts;

public class UnregisteredReason {
    public String reason;
    public String type;
    public String machine;
    public long occurrence;
    public String station;

    public UnregisteredReason(String reason, String type, String machine, long occurrence, String station) {
        this.reason = reason;
        this.type = type;
        this.machine = machine;
        this.occurrence = occurrence;
        this.station = station;
    }
}
