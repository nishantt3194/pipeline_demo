/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.category;

import com.futurealgos.micro.testing.models.base.TestCategory;
import com.futurealgos.micro.testing.utils.specs.dto.SearchResponse;

public class CategorySearch extends SearchResponse {

    public CategorySearch(TestCategory category) {
        super(category.getId().toHexString(), category.getName());
    }


}
