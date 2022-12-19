/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.specs.services;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListSpec<E extends BaseEntity, L extends ListResponse> {

    Page<L> list(E entity, Pageable pageable);

    Page<L> list(Pageable pageable);

}
