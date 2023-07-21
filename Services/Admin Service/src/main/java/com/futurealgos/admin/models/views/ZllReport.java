package com.futurealgos.admin.models.views;

import com.futurealgos.admin.models.primary.*;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Immutable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
@Immutable
@Table(name = "zll_report")
public class ZllReport extends BaseEntity {

    @Column(name = "shift_date")
    LocalDate date;

    @Column(name = "time")
    Double time;

    @Column(name = "total_production")
    Double totalProduction;

    @Column(name = "total_rejection")
    Double totalRejection;


    @OneToOne
    @JoinColumn(name = "entry", referencedColumnName = BaseMap.ID)
    Entry entry;

    @OneToOne
    @JoinColumn(name = "machine", referencedColumnName = BaseMap.ID)
    Machine machine;

    @OneToOne
    @JoinColumn(name = "shift", referencedColumnName = BaseMap.ID)
    Shift shift;

    @OneToOne
    @JoinColumn(name = "area", referencedColumnName = BaseMap.ID)
    Area area;

    @OneToOne
    @JoinColumn(name = "category", referencedColumnName = BaseMap.ID)
    Category category;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("time", time)
                .append("entry", entry.getId())
                .append("machine", machine.getId())
                .append("shift", shift.getId())
                .append("area", area.getId())
                .append("category", category.getId())
                .append("id", id)
                .toString();
    }

    private String processCategory(String category) {
        return category.replaceAll(" ", "_").toLowerCase();
    }

    public Map<String, String> toMap() throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();

        map.put("entry", entry.getId().toString());
        map.put("machine", machine.getName());
        map.put("shift", shift.getName());
        map.put("area", area.getName());

        entry.getDowntimes().forEach(downtime -> {
            try {
                String categoryHeader = processCategory(downtime.getRuntimeCategory().getName());

                if (map.containsKey(categoryHeader)) {
                    Double time = Double.parseDouble(map.get(categoryHeader));
                    time += downtime.getTime();
                    map.put(categoryHeader, time.toString());
                } else {
                    map.put(categoryHeader, downtime.getTime().toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return map;
    }
}
