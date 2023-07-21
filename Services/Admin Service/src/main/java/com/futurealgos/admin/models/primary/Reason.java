package com.futurealgos.admin.models.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.dto.response.downtime.ReasonMinimal;
import com.futurealgos.admin.dto.response.downtime.ReasonSearch;
import com.futurealgos.admin.models.secondary.reason.StationSpecificReason;
import com.futurealgos.admin.utils.enums.BreakdownType;
import com.futurealgos.admin.utils.mappings.ReasonMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = ReasonMap.TABLE)
@Table(indexes = {
        @Index(name = "idx_reason_id_status", columnList = "id, status"),
        @Index(name = "idx_reason_type", columnList = "type")
})
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Reason extends BaseEntity implements Serializable {

    @JsonProperty(ReasonMap.TYPE)
    @Enumerated(EnumType.STRING)
    @Column(name = ReasonMap.TYPE)
    public BreakdownType type;

    @OneToMany(mappedBy = ReasonMap.TABLE, fetch = FetchType.LAZY)
    private Set<StationSpecificReason> associations;

    @JsonProperty(ReasonMap.REASON)
    @Column(name = ReasonMap.REASON, unique = true, nullable = false)
    private String reason;

    @JsonProperty(ReasonMap.DEFAULT_CATEGORY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ReasonMap.DEFAULT_CATEGORY, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16)")
    private Category defaultCategory;

    @Override
    public ReasonSearch search() {
        return ReasonSearch.builder()
                .label(reason)
                .category(defaultCategory != null ? defaultCategory.getId().toString() : "")
                .identifier(id.toString())
                .status(getStatus())
                .type(getType() != null ? getType().name().replaceAll("_", " ").trim() : "--")
                .categoryLabel(getDefaultCategory().getName())
                .build();
    }

    @Override
    public ReasonMinimal minimal() {
        return ReasonMinimal.builder()
                .category(defaultCategory.getName())
                .type(type.name())
                .reason(reason)
                .build();
    }
}
