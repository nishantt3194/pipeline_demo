/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.specs.dto;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class SearchResponse {
    public String identifier;
    public String label;

    public SearchResponse(String identifier, String label) {
        this.identifier = identifier;
        this.label = label;
    }
}
