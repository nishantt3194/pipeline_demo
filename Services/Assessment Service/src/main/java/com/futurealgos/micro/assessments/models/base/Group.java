/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.dto.payload.NewGroup;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.models.base
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "examinee_group")
public class Group extends BaseEntity implements Serializable {

    public enum Status{
        ACTIVE,INACTIVE
    }

    public String name;

    public String description;

    public Status status;

    @DocumentReference(collection = "partners")
    public Partner partner;

    @Builder.Default
    @DocumentReference(collection = "examinee")
    public List<Examinee> examinees = new ArrayList<>();


    public static Group convert(NewGroup group) {
        return Group.builder()
                .name(group.name())
                .status(Status.ACTIVE)
                .description(group.description())
                .build();
    }
}
