/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.utils.specs.dto;


import com.futurealgos.micro.documents.dao.BaseEntity;

public abstract class InfoResponse<E extends BaseEntity> {
    String identifier;

    public abstract InfoResponse<E> build(E entity);
}
