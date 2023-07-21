package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.machine.MachineInfo;
import com.futurealgos.admin.dto.response.machine.MachineMinimal;
import com.futurealgos.admin.dto.response.machine.MachineSearch;
import com.futurealgos.admin.models.secondary.machine.MachineSpecificRequirement;
import com.futurealgos.admin.models.secondary.machine.Precheck;
import com.futurealgos.admin.models.secondary.machine.Station;
import com.futurealgos.admin.utils.mappings.AreaMap;
import com.futurealgos.admin.utils.mappings.MachineMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import com.futurealgos.specs.objects.DefaultSearchResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = MachineMap.TABLE)
@Table(indexes = {
        @Index(name = "idx_machine_id_name", columnList = "id, name"),
        @Index(name = "idx_machine_area", columnList = "area"),
        @Index(name = "idx_machine_status", columnList = "status")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Machine extends BaseEntity implements Serializable {

    @JsonProperty(MachineMap.NAME)
    @Column(name = MachineMap.NAME, nullable = false, unique = true)
    private String name;

    @JsonProperty(MachineMap.PROD_LIMIT)
    @Column(name = MachineMap.PROD_LIMIT, nullable = false)
    private Integer prodLimit;

    @JsonProperty(MachineMap.TOLERANCE)
    @Column(name = MachineMap.TOLERANCE, nullable = false)
    private Integer tolerance;

    @JsonProperty(MachineMap.SPEED)
    @Column(name = MachineMap.SPEED, nullable = false)
    private Double speed;

    @JsonProperty(MachineMap.STRETCHED_TARGET)
    @Column(name = MachineMap.STRETCHED_TARGET)
    private Integer stretchedTarget;

    @JsonProperty(MachineMap.LSA_TARGET)
    @Column(name = MachineMap.LSA_TARGET)
    private Integer lsaTarget;

    @ManyToOne
    @JoinColumn(name = AreaMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Area area;

    @Builder.Default
    @OneToMany(mappedBy = MachineMap.TABLE, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Station> stations = new HashSet<>();
    @Builder.Default

    @OneToMany(mappedBy = MachineMap.TABLE, fetch = FetchType.EAGER)
    private Set<Precheck> prechecks = new HashSet<>();

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "machines")
    private Set<User> operators = new HashSet<>();

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private Set<MachineSpecificRequirement> requirements;

    @Column(name = MachineMap.OEE_TARGET)
    @JsonProperty(MachineMap.OEE_TARGET)
    private Double oeeTarget ;

    @Column(name = MachineMap.BASE_VALUE)
    @JsonProperty(MachineMap.BASE_VALUE)
    private Double baseValue;

    @Override
    public MachineInfo info() {
        return MachineInfo.builder()
                .id(id.toString())
                .name(name)
                .speed(speed)
                .area(area.getName())
                .lsaTarget(lsaTarget)
                .prodLimit(prodLimit)
                .tolerance(tolerance)
                .oeeTarget(oeeTarget)
                .baseValue(baseValue)
                .stretchedTarget(stretchedTarget)
                .status(status)
                .precheck(prechecks.stream().map(Precheck::info).toList())
                .stations(stations.stream().map(Station::info).toList())
                .build();
    }

    @Override
    public MachineMinimal minimal() {
        return MachineMinimal.builder()
                .id(id.toString())
                .area(area.getName())
                .name(name)
                .status(status)
                .totalStation(stations.size())
                .build();
    }

    @Override
    public MachineSearch search() {
        return MachineSearch.builder()
                .identifier(id.toString())
                .label(name)
                .speed(speed)
                .tolerance(Double.valueOf(tolerance))
                .production(Double.valueOf(prodLimit))
                .build();
    }
}
