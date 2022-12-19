/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.specs.services;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.specs.dto.InfoResponse;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;

public interface ServiceSpec<E extends BaseEntity, C,
        I extends InfoResponse<E, I>, L extends ListResponse>
        extends CreateSpec<C>, FetcherSpec<E>, InfoSpec<E, I>, ListSpec<E, L> {

    Long count();
}
