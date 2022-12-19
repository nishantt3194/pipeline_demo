/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.specs.services;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;

public interface InfoSpec<E extends BaseEntity, I extends InfoResponse<E, I>> {
    I info(String identifier);
}
