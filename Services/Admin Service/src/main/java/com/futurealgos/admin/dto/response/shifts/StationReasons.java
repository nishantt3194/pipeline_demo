package com.futurealgos.admin.dto.response.shifts;

import java.util.Set;


public class StationReasons {
    public String station;
    public Set<String> reasons;

    public StationReasons(String station, Set<String> reasons) {
        this.station = station;
        this.reasons = reasons;
    }
}
