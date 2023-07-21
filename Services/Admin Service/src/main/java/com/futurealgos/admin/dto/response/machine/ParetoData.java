package com.futurealgos.admin.dto.response.machine;

import java.util.ArrayList;
import java.util.List;

public class ParetoData {
    public List<String> reasons;
    public List<Long> downtime;
    public List<Double> percentage;
    public List<Double> cumulativePercentage;
    public long shifts;

    public ParetoData() {
        this.reasons = new ArrayList<>();
        this.downtime = new ArrayList<>();
        this.percentage = new ArrayList<>();
        this.cumulativePercentage = new ArrayList<>();
        this.shifts = 0;
    }

    @Override
    public String toString() {
        return "ParetoData{" +
                "reasons=" + reasons +
                ", downtime=" + downtime +
                ", percentage=" + percentage +
                ", cumulativePercentage=" + cumulativePercentage +
                '}';
    }
}
