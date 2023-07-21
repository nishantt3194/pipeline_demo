package com.futurealgos.admin.models.views;

import com.futurealgos.admin.models.primary.Area;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.data.utils.mappings.BaseMap;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "user_area_association")
public class UserAreaAssociation {
    @Id
    @Column(name = BaseMap.ID, columnDefinition = "BINARY(16)")
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = BaseMap.ID)
    private User user;

    @OneToOne
    @JoinColumn(name = "area", referencedColumnName = BaseMap.ID)
    private Area area;

}
