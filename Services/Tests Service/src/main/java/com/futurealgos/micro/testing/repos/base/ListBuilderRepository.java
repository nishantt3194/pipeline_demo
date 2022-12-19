/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos.base;


import com.futurealgos.micro.testing.models.root.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ListBuilderRepository<T extends BaseEntity> {

    Page<T> list(Class<T> entity, Pageable pageable);

    Page<T> list(Class<T> entity, String[] fields, Pageable pageable);

    Page<T> list(Class<T> entity, String[] fields, Map<String, Object> map, Pageable pageable);

}
