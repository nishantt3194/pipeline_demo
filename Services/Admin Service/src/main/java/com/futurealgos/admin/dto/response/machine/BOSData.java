package com.futurealgos.admin.dto.response.machine;

import java.util.ArrayList;
import java.util.List;

public class BOSData {
    public List<String> labels;
    public List<Long> goodQuantities;
    public List<Long> weekly;
    public List<Long> weekly4;
    public List<Long> weekly12;
    public List<Long> lsa;
    public List<Long> stretched;

    public BOSData() {
        this.labels = new ArrayList<>();
        this.goodQuantities = new ArrayList<>();
        this.weekly = new ArrayList<>();
        this.weekly4 = new ArrayList<>();
        this.weekly12 = new ArrayList<>();
        this.lsa = new ArrayList<>();
        this.stretched = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "BOSData{" +
                "labels=" + labels +
                ", goodQuantities=" + goodQuantities +
                ", weekly=" + weekly +
                ", weekly4=" + weekly4 +
                ", weekly12=" + weekly12 +
                ", lsa=" + lsa +
                ", stretched=" + stretched +
                '}';
    }
}
