/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.norms;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class NormsInfo extends InfoResponse<Norm, NormsInfo> {

    private String name;

    private String description;
    private String status;
    private String createdOn;
    private Boolean isPredefined;
    private String type;
    private String template;
    private List<String> classifications;

    public static NormsInfo populate(Norm entity) {
        List<String> classifications = new ArrayList<>();
        for (Classification classification: entity.getClassification()) {
            classifications.add(classification.getValue());
        }

        return NormsInfo.builder()
                .identifier(entity.getId().toHexString())
                .name(entity.getName())
                .status(entity.getStatus().getName())
                .createdOn(new SimpleDateFormat("dd MMM yyyy").format(entity.getCreatedOn()))
                .description(entity.getDescription())
                .isPredefined(entity.getIsPredefined())
                .type(entity.getType().name())
                .template(entity.getTemplate().name())
                .classifications(classifications)
                .build();
    }
}
