/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.norms;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.utils.specs.dto.SearchResponse;

public class NormsSearch extends SearchResponse {


    public NormsSearch(Norm norm) {
        super(norm.getId().toHexString(), norm.getName());
    }
}
