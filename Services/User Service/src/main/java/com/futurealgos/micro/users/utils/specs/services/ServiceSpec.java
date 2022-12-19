/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils.specs.services;


import com.futurealgos.micro.users.models.root.BaseEntity;
import com.futurealgos.micro.users.utils.specs.dto.InfoResponse;
import com.futurealgos.micro.users.utils.specs.dto.ListResponse;

public interface ServiceSpec<E extends BaseEntity,
        C extends Record, I extends InfoResponse<E>, L extends ListResponse>
        extends ModSpec<C>, FetcherSpec<E>, InfoSpec<E, I>, ListSpec<E, L> {

    Long count();
}
