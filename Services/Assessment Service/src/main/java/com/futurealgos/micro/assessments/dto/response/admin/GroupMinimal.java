/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Group;
import com.futurealgos.micro.assessments.utils.specs.dto.ListResponse;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@SuperBuilder
public class GroupMinimal extends ListResponse {

    public String name;

    public String description;

    public String status;

    public Integer count;

    public List<ExamineeSearch> examinees;

    public static GroupMinimal convert(Group group){
        return GroupMinimal.builder()
                .identifier(group.getId().toHexString())
                .name(group.name)
                .description(group.description)
                .count(group.getExaminees() != null ? group.getExaminees().size() : 0)
                .status(group.status.name())
                .examinees(group.getExaminees()
                        .stream().map(ExamineeSearch::minimal).toList())
                .build();
    }
}
