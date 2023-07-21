package com.futurealgos.admin.dto.response.machine;

import java.util.ArrayList;
import java.util.List;

public class OeePareto {

    public List<String> category;
    public List<Long> downtime;
    public List<Double> percentage;
    public List<Double> cumulativePercentage;
    public long shifts;

    public OeePareto() {
        this.category = new ArrayList<>();
        this.downtime = new ArrayList<>();
        this.percentage = new ArrayList<>();
        this.cumulativePercentage = new ArrayList<>();
        this.shifts = 0;
    }
}
