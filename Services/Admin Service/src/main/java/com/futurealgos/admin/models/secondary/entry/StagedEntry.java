package com.futurealgos.admin.models.secondary.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.payload.entry.EntryPayload;
import com.futurealgos.admin.dto.payload.entry.StagedBreakdownPayload;
import com.futurealgos.admin.dto.payload.entry.StagedTemplatePayload;
import com.futurealgos.admin.dto.response.entry.EntryMinimal;
import com.futurealgos.admin.dto.response.entry.EntryReasons;
import com.futurealgos.admin.dto.response.entry.StagedEntryInfo;
import com.futurealgos.admin.models.primary.*;
import com.futurealgos.admin.utils.mappings.StagedEntryMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = StagedEntryMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID,
                columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class StagedEntry extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1618731589267206518L;

    @JsonProperty(StagedEntryMap.SHIFT)
    @ManyToOne
    @JoinColumn(name = StagedEntryMap.SHIFT, referencedColumnName = BaseMap.ID)
    public Shift shift;

    @ManyToOne
    @JsonProperty(StagedEntryMap.AREA)
    @JoinColumn(name = StagedEntryMap.AREA, referencedColumnName = BaseMap.ID)
    public Area area;

    @JsonProperty(StagedEntryMap.ENTRY_DATE)
    @Column(name = StagedEntryMap.ENTRY_DATE)
    public LocalDate shiftDate;

    @ManyToOne
    @JsonProperty(StagedEntryMap.PRODUCT)
    @JoinColumn(name = StagedEntryMap.PRODUCT)
    public Product product;
    @ElementCollection
    @CollectionTable(
            name = "staged_entry_variants",
            joinColumns = {
                    @JoinColumn(name = StagedEntryMap.TABLE,
                            referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
            }
    )
    @JsonProperty(StagedEntryMap.VARIANTS)
    @Column(name = StagedEntryMap.VARIANTS)
    public List<String> variants;

    @ManyToOne
    @JsonProperty(StagedEntryMap.MACHINE)
    @JoinColumn(name = StagedEntryMap.MACHINE, referencedColumnName = BaseMap.ID)
    public Machine machine;

    @ManyToOne
    @JsonProperty(StagedEntryMap.OPERATOR)
    @JoinColumn(name = StagedEntryMap.OPERATOR, referencedColumnName = BaseMap.ID)
    public User operator;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entry")
    public Set<StagedBreakdown> downtimes = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entry")
    public Set<StagedTemplate> quantities = new HashSet<>();

    @JsonProperty(StagedEntryMap.REMARKS)
    @Column(name = StagedEntryMap.REMARKS)
    public String remarks;



    public void populate(
            Area area,
            Product product,
            LocalDate shiftDate,String remarks,
            Machine machine,
            User operator,
            Shift shift,
            List<String> variants
    ) {

        this.area = area;
        this.product = product;
        this.machine = machine;
        this.operator = operator;
        this.variants = variants;
        this.shiftDate = shiftDate;
        this.shift = shift;
        this.remarks = remarks;
    }

    @Override
    public EntryPayload info() {
        return EntryPayload.from(this);
    }

    @Override
    public EntryMinimal minimal() {
        return EntryMinimal.builder()
                .id(this.getId().toString())
                .shift(this.shift!=null? this.shift.getName():"--")
                .area(this.area.getName())
                .operator(this.operator != null ? this.operator.getFullName() : "--")
                .product(this.product.getCatalogNum() + " - " + this.product.getDescription())
                .date(this.shiftDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .machine(this.machine.getId().toString())
                .machineName(this.machine.getName())
                .reasons(mapReasons())
                .remarks(this.remarks)
                .build();
    }


    @Override
    public EntryMinimal  search() {
        return EntryMinimal.builder()
                .id(this.getId().toString())
                .shift(this.shift!=null? this.shift.getName():"--")
                .area(this.area.getName())
                .operator(this.operator != null ? this.operator.getFullName() : "--")
                .product(this.product.getCatalogNum() + " - " + this.product.getDescription())
                .date(this.shiftDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .machine(this.machine.getId().toString())
                .machineName(this.machine.getName())
                .reasons(mapReasons())
                .remarks(this.remarks)
                .build();


    }

    public List<EntryReasons> mapReasons(){
        List<EntryReasons> reasonList = new ArrayList<>();
        for (StagedBreakdown downtime : downtimes) {
            String reason = downtime.getReason().getReason();
            double time = downtime.getTime()!=null?downtime.getTime():0.0;
            EntryReasons reasons = EntryReasons.builder()
                    .reason(reason)
                    .time(time)
                    .build();
            reasonList.add(reasons);
        }
        return reasonList;
    }
}
