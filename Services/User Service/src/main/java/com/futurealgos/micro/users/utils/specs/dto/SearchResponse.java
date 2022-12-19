/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils.specs.dto;

public abstract class SearchResponse {
    public String identifier;
    public String label;

    public SearchResponse(String identifier, String username) {
        this.identifier=identifier;
        this.label=username;
    }
}
