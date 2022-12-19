/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Group;
import com.futurealgos.micro.assessments.utils.specs.dto.SearchResponse;

public class GroupSearch extends SearchResponse {

    public GroupSearch(Group group) {
        super(group.getId().toHexString(), group.name);
    }
}
