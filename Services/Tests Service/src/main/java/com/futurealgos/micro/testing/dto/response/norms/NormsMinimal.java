/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.norms;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@SuperBuilder
public class NormsMinimal extends ListResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 8829609732567074183L;
    private String name;

    private String description;

    private String status;
    private String type;
    private String template;
    private List<String> classifications;

    public static NormsMinimal build(Norm norm) {
        List<String> classifications = new ArrayList<>();
        for (Classification classification: norm.getClassification()) {
            classifications.add(classification.getValue());
        }

        return NormsMinimal.builder()
                .identifier(norm.getId().toHexString())
                .name(norm.getName())
                .template((!norm.getIsPredefined()) ? null : norm.getTemplate().name())
                .type(norm.getType().name())
                .status(norm.getStatus().getName())
                .description(norm.getDescription())
                .classifications(classifications)
                .build();
    }


}
