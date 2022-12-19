/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.utils.specs.services;

public interface ModSpec<C extends Record> {

    void create(C payload, String admin);

//    void update(C payload, String admin);
}
