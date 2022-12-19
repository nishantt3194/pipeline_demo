/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.specs.services;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import org.bson.types.ObjectId;

import java.util.List;

public interface FetcherSpec<E extends BaseEntity> {

    E fetch(String identifier);

    E fetch(ObjectId identifier);

    List<E> fetchAll(List<String> identifier);
}
