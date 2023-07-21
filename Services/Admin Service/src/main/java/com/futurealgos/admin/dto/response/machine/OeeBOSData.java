package com.futurealgos.admin.dto.response.machine;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
public class OeeBOSData {

    public List<String> labels;
    public List<Double> weekly;
    public List<Double> weekly4;
    public List<Double> weekly12;
    public List<Long> lsa;
    public List<Double> oeeTarget;
    public List<Double> baseValue;

    public OeeBOSData() {
        this.labels = new ArrayList<>();
        this.weekly = new ArrayList<>();
        this.weekly4 = new ArrayList<>();
        this.weekly12 = new ArrayList<>();
        this.lsa = new ArrayList<>();
        this.oeeTarget = new ArrayList<>();
        this.baseValue = new ArrayList<>();
    }
}
