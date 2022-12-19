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

/**
 * Package: com.futurealgos.micro.testing.utils.specs.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public interface ImmutableSpec<E extends BaseEntity,
        I extends InfoResponse<E>, L extends ListResponse>
        extends FetcherSpec<E>, InfoSpec<E, I>, ListSpec<E, L> {

    Long count();
}
