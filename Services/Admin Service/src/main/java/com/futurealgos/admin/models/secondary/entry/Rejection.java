package com.futurealgos.admin.models.secondary.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.admin.models.primary.Entry;
import com.futurealgos.admin.utils.enums.Sign;
import com.futurealgos.admin.utils.mappings.EntryMap;
import com.futurealgos.admin.utils.mappings.RejectionMap;
import com.futurealgos.data.models.BaseEntity;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = RejectionMap.TABLE)
@Table(name = RejectionMap.TABLE)
@AttributeOverrides({
        @AttributeOverride(name = BaseMap.ID, column = @Column(name = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))"))
})
public class Rejection extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1405597905573388768L;

    @JsonProperty(RejectionMap.SIGN)
    @Enumerated(value = EnumType.STRING)
    @Column(name = RejectionMap.SIGN)
    public Sign sign;

    @JsonProperty(RejectionMap.DESCRIPTION)
    @Column(name = RejectionMap.DESCRIPTION)
    public String description;

    @JsonProperty(RejectionMap.VALUE)
    @Column(name = RejectionMap.VALUE)
    public Double value;

    @JsonProperty(RejectionMap.ACTUAL_VALUE)
    @Column(name = RejectionMap.ACTUAL_VALUE)
    public Double actualValue;

    @ManyToOne
    @JoinColumn(name = EntryMap.TABLE, referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Entry entry;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", referencedColumnName = BaseMap.ID, columnDefinition = "BINARY(16) DEFAULT ((UUID_TO_BIN(UUID())))")
    public Template template;

}
