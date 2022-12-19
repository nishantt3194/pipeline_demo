/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.specs.services;


import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.specs.dto.InfoResponse;

/**
 * Package: com.futurealgos.micro.testing.utils.specs.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public interface UpdateSpec<E extends BaseEntity, U, I extends InfoResponse<E, I>> {

    I update(U payload, String admin);
}
