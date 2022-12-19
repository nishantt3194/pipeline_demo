/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.utils.specs.dto.SearchResponse;

public class InterpretationSearchResponse extends SearchResponse {

    public InterpretationSearchResponse(String label, String identifier) {
        super(identifier, label);
    }
}
