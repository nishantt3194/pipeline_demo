/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.specs.services;


import com.futurealgos.micro.assessments.models.root.BaseEntity;

/**
 * Package: com.futurealgos.micro.testing.utils.specs.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public interface InternalUpdateSpec<E extends BaseEntity> {

    E update(E payload, String admin);

}
