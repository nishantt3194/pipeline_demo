/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils.specs.dto;


import com.futurealgos.micro.users.models.root.BaseEntity;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class InfoResponse<E extends BaseEntity> {
    String identifier;
}
