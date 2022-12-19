/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.specs.services;

import com.futurealgos.micro.assessments.models.root.BaseEntity;

public interface CreateSpec<E extends BaseEntity, C> {

    E create(C payload, String admin) throws Exception;

}
