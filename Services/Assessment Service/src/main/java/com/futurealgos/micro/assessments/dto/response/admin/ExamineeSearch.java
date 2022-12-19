/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.utils.specs.dto.SearchResponse;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@SuperBuilder
public class ExamineeSearch extends SearchResponse {

    public String email;
    @Builder.Default
    public List<GroupSearch> groups = new ArrayList<>();

    public static ExamineeSearch from(Examinee examinee) {
        return ExamineeSearch.builder()
                .label(examinee.getName())
                .identifier(examinee.getId().toHexString())
                .email(examinee.getEmail())
                .groups(examinee.getGroups()
                        .stream().map(GroupSearch::new).toList())
                .build();
    }

    public static ExamineeSearch minimal(Examinee examinee) {
        return ExamineeSearch.builder()
                .label(examinee.getName())
                .identifier(examinee.getId().toHexString())
                .email(examinee.getEmail())
                .build();
    }
}
