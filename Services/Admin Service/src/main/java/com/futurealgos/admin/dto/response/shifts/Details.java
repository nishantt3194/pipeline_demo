package com.futurealgos.admin.dto.response.shifts;


import com.futurealgos.admin.models.primary.Shift;

import java.time.format.DateTimeFormatter;

public class Details {
    public String identifier;
    public String name;
    public String area;
    public String startTime;
    public String stopTime;

    public Details(String identifier, String name, String area, String startTime, String stopTime) {
        this.identifier = identifier;
        this.name = name;
        this.area = area;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public static Details fromShiftDetails(Shift details) {

        return new Details(
                details.getId().toString(),
                details.getName(),
                details.getArea().getName(),
                details.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a")),
                details.getStopTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
        );
    }
}
