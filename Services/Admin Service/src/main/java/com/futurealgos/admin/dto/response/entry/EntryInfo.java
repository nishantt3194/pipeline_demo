package com.futurealgos.admin.dto.response.entry;

import com.futurealgos.admin.dto.response.shifts.DowntimeInfo;
import com.futurealgos.admin.models.primary.Downtime;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.models.primary.Shift;
import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.Rejection;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.models.secondary.product.Variant;
import com.futurealgos.admin.utils.constants.ConstantService;
import com.futurealgos.admin.utils.enums.BreakdownType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public record EntryInfo(

        String id ,

        Details details,
        String timestamp,
        String date,
        String remarks,
        String product,
        Double totalPDT,
        Double totalUDT,
        Double totalUptime,
        Double totalDowntime,

        Double production,
        Double rejection,
        Double goodProduction,
        Double rejectionPercentage,

        Double speedFromControlPlan,
        Double oee,
        String machineName,
        String machineId,
        List<DowntimeInfo> downtimes,

        List<Quantity> quantities,

        String operator,
        List<String > variant,

        Double utilizationPercentage,
        Double scheduleProductionTime,
        Double valueOperatingTime,

        List<Double > actualProductions,
        List<Double> actualRejections


) {

    public static EntryInfo fromShiftEntry(Entry entry) {
        DateFormat df = new SimpleDateFormat("hh:mm dd MMM yyyy");
        List<DowntimeInfo> b = new ArrayList<>();

        for (Downtime downtime :
                entry.getDowntimes()) {
            String association = "";
            if (downtime.type.equals(BreakdownType.COMMON)) association = entry.getMachine().getArea().getName();
//            else if (downtime.type.equals(BreakdownType.PLANNED)) association = "--";
            else {
                association = entry.getMachine().getStations().stream()
                        .filter(stn -> stn.getId().equals(downtime.association))
                        .map(Station::getName)
                        .toList().get(0);
            }

            b.add(new DowntimeInfo(downtime.getCustomReason(), downtime.getTime(),
                    downtime.getType().name(), association));
        }

        List<Quantity> quantities = new ArrayList<>();

        if (entry.getProductions() != null && !entry.getProductions().isEmpty()) {
            quantities.addAll(
                    entry.getProductions().stream().map(p -> {
                        return new Quantity(p.description, p.value);
                    }).toList()
            );
        }

        if (entry.getRejections() != null && !entry.getRejections().isEmpty()) {
            quantities.addAll(
                    entry.getRejections().stream().map(r -> {
                        return new Quantity(r.description, r.value);
                    }).toList()
            );
        }



        ConstantService constants;
        return new EntryInfo(
                entry.getId().toString(),
                new Details(entry.getShift()),
                df.format(entry.getCreatedOn()!=null?entry.getCreatedOn():new Date()),
                entry.getShiftDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                entry.getRemarks(),
                entry.getProduct().getDescription(),
                entry.getTotalPDT(),
                entry.getTotalUDT(),
                entry.getTotalUptime(),
                entry.getTotalDowntime(),
                entry.getTotalProduction(),
                entry.getTotalRejection(),
                entry.getGoodProduction(),
                entry.getRejectionPercent(),
                entry.getExpectedMachineSpeed(),
                entry.getOee(),
                entry.getMachine().getName(),
                entry.getMachine().getId().toString(),
                b,
                quantities,
                entry.getOperator() != null ? entry.getOperator().getFullName() : "--",
                entry.getVariant().stream().map(Variant::getName).toList(),
                entry.utilizationPercentage(),
                entry.actualProductionTime(),
                entry.valueOperatingTime(),
                entry.getProductions().stream().map(Production::getActualValue).toList(),
                entry.getRejections().stream().map(Rejection::getActualValue).toList()
        );
    }

    private static final class Quantity {
        public String description;
        public Double value;

        public Quantity(String description, Double value) {
            this.description = description;
            this.value = value;
        }
    }

    private static final class Details {
        public String name;
        public String startTime;
        public String stopTime;
        public String area;

        public Details(Shift details) {
            DateFormat df = new SimpleDateFormat("hh:mm a");
            this.name = details.getName();
            this.startTime = details.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
            this.stopTime = details.getStopTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
            this.area = details.getArea().getName();
        }
    }


}
