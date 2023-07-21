package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.entry.EntryInfo;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.entry.EntryReasons;
import com.futurealgos.admin.models.secondary.entry.Production;
import com.futurealgos.admin.models.secondary.entry.Rejection;
import com.futurealgos.admin.models.secondary.product.Variant;
import com.futurealgos.admin.utils.constants.ConstantService;
import com.futurealgos.admin.utils.mappings.EntryMap;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.admin.utils.mappings.ProductMap;
import com.futurealgos.admin.utils.mappings.ShiftMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.crypto.spec.PSource;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = EntryMap.TABLE)
@Table(indexes = {
        @Index(name = "idx_entry_id_unq", columnList = "id", unique = true),
        @Index(name = "idx_entry_shift_date", columnList = "shift_date"),
        @Index(name = "idx_entry_shift_date_machine", columnList = "shift_date, machine")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Entry extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = ShiftMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Shift shift;

    @JsonProperty(EntryMap.SHIFT_DATE)
    @Column(name = EntryMap.SHIFT_DATE, columnDefinition = "DATE", nullable = false)
    private LocalDate shiftDate;

    @JsonProperty(EntryMap.REMARKS)
    @Column(name = EntryMap.REMARKS)
    private String remarks;
    @ManyToOne
    @JoinColumn(name = ProductMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Product product;

    @Builder.Default
    @OneToMany(mappedBy = EntryMap.TABLE, orphanRemoval = false,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<Downtime> downtimes = new HashSet<>();

    @JsonProperty(EntryMap.TOTAL_PDT)
    @Column(name = EntryMap.TOTAL_PDT)
    private Double totalPDT;

    @JsonProperty(EntryMap.TOTAL_UDT)
    @Column(name = EntryMap.TOTAL_UDT)
    private Double totalUDT;
    @JsonProperty(EntryMap.EXPECTED_MACHINE_SPEED)
    @Column(name = EntryMap.EXPECTED_MACHINE_SPEED)
    private Double expectedMachineSpeed;

    @JsonProperty(EntryMap.TOTAL_PRODUCTION)
    @Column(name = EntryMap.TOTAL_PRODUCTION)
    private Double totalProduction;

    @JsonProperty(EntryMap.TOTAL_REJECTION)
    @Column(name = EntryMap.TOTAL_REJECTION)
    private Double totalRejection;

    @JsonProperty(EntryMap.GOOD_PRODUCTION)
    @Column(name = EntryMap.GOOD_PRODUCTION)
    private Double goodProduction;

    @JsonProperty(EntryMap.OEE_TARGET)
    @Column(name = EntryMap.OEE_TARGET)
    private Double oeeTarget;

    @Builder.Default
    @OneToMany(mappedBy = "entry")
    private List<Production> productions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "entry")
    private List<Rejection> rejections = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = MachineMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Machine machine;
    @ManyToOne
    @JoinColumn(name = "operator", referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private User operator;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "entry_variant",
            joinColumns = {@JoinColumn(name = "entry_id", referencedColumnName = BaseMap.ID,
                    columnDefinition = "BINARY(16)")},
            inverseJoinColumns = {@JoinColumn(name = "variant_id", referencedColumnName = BaseMap.ID,
                    columnDefinition = "BINARY(16)")})
    private List<Variant> variant = new ArrayList<>();

    @JsonProperty(EntryMap.PERFORMANCE)
    @Column(name = EntryMap.PERFORMANCE)
    private Double performance;

    @JsonProperty(EntryMap.QUALITY)
    @Column(name = EntryMap.QUALITY)
    private Double quality;

    @JsonProperty(EntryMap.OEE)
    @Column(name = EntryMap.OEE)
    private Double oee;

    @JsonProperty(EntryMap.AVAILABILITY)
    @Column(name = EntryMap.AVAILABILITY)
    private Double availability;

    @JsonProperty(EntryMap.LSA_TARGET)
    @Column(name = EntryMap.LSA_TARGET)
    private Double lsaTarget;



    public Double getTotalUptime() {
        return 480 - getTotalDowntime();
    }

    public Double getTotalDowntime() {
        if (this.downtimes != null && !this.downtimes.isEmpty()) {
            return this.downtimes.stream().map(Downtime::getTime).filter(Objects::nonNull).reduce(0.00, Double::sum);
        } else return 0.0;
    }

    public double getRejectionPercent() {
        DecimalFormat dec = new DecimalFormat("#0.00");
        if (this.shift.area.getName().equals("Hypo Blister Packaging")) {

            Rejection input_4 = this.rejections.stream().filter(rejection -> rejection.description.equalsIgnoreCase("Laminate+MG rejection in Secondary packaging(kg)")).findAny().orElse(null);
            Rejection input_5 = this.rejections.stream().filter(rejection -> rejection.description.equalsIgnoreCase("Laminate+MG rejection in Primary packaging(kg)")).findAny().orElse(null);
            if (input_4 != null && input_5 != null) {
                return Double.parseDouble(dec.format(((this.totalRejection / this.totalProduction) + (((0.2 / 0.0056) * input_4.value * 0.153) + input_5.value) / ((this.goodProduction * 0.00327) * 2)) * 100));
            } else return 0.0;
        }
        return Double.parseDouble(dec.format((this.totalRejection / this.totalProduction) * 100));

    }

    public Double getBreakDownsInHours() {
        return (totalUDT / 60);
    }

    public Double getActualSpeed() {
        return (this.totalProduction / this.getTotalUptime());
    }


    public void refreshPerformance() {
        if (this.getActualSpeed().equals(0.0) && this.expectedMachineSpeed.equals(0.0)) this.performance = 0.0;
        this.performance = (this.getActualSpeed() / this.expectedMachineSpeed) * 100;
    }


    public void refreshQuality() {
        this.quality = (this.goodProduction / this.totalProduction) * 100;
    }


    public Double utilizationPercentage() {
        var scheduledProductionTime = Double.valueOf(getShift().getTotalTime());
        return ((scheduledProductionTime - utilizationLoss()) / scheduledProductionTime) * 100;
    }


    public double actualProductionTime() {

        double totalTime = 480.00;
        double apt= totalTime-utilizationLoss();
        return  apt;
    }


    public double utilizationLoss() {
        double finalDowntime =  getDowntimes().stream().filter(downtime -> downtime.getRuntimeCategory() != null && downtime.getRuntimeCategory()
                        .getParentCategory().getName()
                        .equalsIgnoreCase("utilization loss"))
                .mapToDouble(Downtime::getTime).sum();
        return finalDowntime;
    }


    public void refreshOee(ConstantService constants) {


        oee = valueOperatingTime() / actualProductionTime() * 100;
    }

    public double valueOperatingTime() {
        var goodManufacturingQuantity = getTotalProduction() - getTotalRejection();
        return (double) Math.round((goodManufacturingQuantity / getExpectedMachineSpeed()) * 100) / 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Entry entry = (Entry) o;

        return Objects.equals(id, entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public EntryMinimal minimal() {

        DecimalFormat dec = new DecimalFormat("#0.00");
        return EntryMinimal.builder()
                .id(this.id.toString())
                .machineName(machine.getName())
                .shift(this.shift.name)
                .date(this.shiftDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .oee((Math.round(this.oee * 100)) / 100.00)
                .product(this.product.getCatalogNum() + " - " + this.product.getDescription())
                .production(this.getTotalProduction())
                .rejection(this.getTotalRejection())
                .downtime(this.getTotalDowntime())
                .goodProduction(this.getGoodProduction())
                .operator(operator != null ? this.operator.getFullName() : "--")
                .remarks(this.remarks)
                .rejectionPercentage(this.getRejectionPercent())
                .utilizationPercentage(this.utilizationPercentage())
                .valueOperatingTime(this.valueOperatingTime())
                .scheduledProductionTime(this.actualProductionTime())
                .rejectionPercentage(this.getRejectionPercent())
                .area(this.shift.area.getName())
                .reasons(mapReasons())
                .bottleNeckSpeed(this.expectedMachineSpeed)
                .actualSpeed(this.getActualSpeed())
                .build();
    }

    @Override
    public EntryMinimal search() {


        DecimalFormat dec = new DecimalFormat("#0.00");
        return EntryMinimal.builder()
                .id(this.id.toString())
                .machineName(machine.getName())
                .shift(this.shift.name)
                .date(this.shiftDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .oee((Math.round(this.oee * 100)) / 100.00)
                .product(this.product.getCatalogNum() + " - " + this.product.getDescription())
                .production(this.getGoodProduction())
                .rejection(this.getTotalRejection())
                .goodProduction(this.getGoodProduction())
                .downtime(this.getTotalDowntime())
                .operator(operator != null ? this.operator.getFullName() : "--")
                .remarks(this.remarks)
                .rejectionPercentage(this.getRejectionPercent())
                .area(this.shift.area.getName())
                .build();
    }

    //    public String getBreakdowns(){
//        return this.getDowntimes().stream().map(Downtime::getReason).map(Reason::getReason).collect(Collectors.joining(", "));
//    }

    public List<EntryReasons> mapReasons() {
        List<EntryReasons> reasonList = new ArrayList<>();
        for (Downtime downtime : downtimes) {
            String reason = downtime.getReason() != null ? downtime.getReason().getReason() : null;
            double time = downtime.getTime()!=null?downtime.getTime():0.0;
            if (reason != null) {
                EntryReasons reasons = EntryReasons.builder()
                        .reason(reason)
                        .time(time)
                        .build();
                reasonList.add(reasons);
            }
        }
        return reasonList;
    }

    @Override
    public EntryInfo info() {
        return EntryInfo.fromShiftEntry(this);
    }
}
