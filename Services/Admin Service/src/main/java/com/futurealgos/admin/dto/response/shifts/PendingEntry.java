package com.futurealgos.admin.dto.response.shifts;


import com.futurealgos.admin.models.primary.Entry;

import java.time.format.DateTimeFormatter;

public class PendingEntry {
    public String id;
    public String shift;
    public String area;
    public String machine;
    public String date;

    public PendingEntry(String id, String shift, String area, String machine, String date) {
        this.id = id;
        this.shift = shift;
        this.area = area;
        this.machine = machine;
        this.date = date;
    }

    public static PendingEntry fromEntry(Entry entry) {
        return new PendingEntry(
                entry.getId().toString(),
                entry.getShift().getName(),
                entry.getShift().getArea().getName(),
                entry.getMachine().getName(),
                entry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
