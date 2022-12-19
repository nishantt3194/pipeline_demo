/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;


import com.futurealgos.micro.users.models.base.PreRegister;
import org.springframework.stereotype.Repository;

@Repository
public interface PreRegistrationRepo extends BaseRepo<PreRegister> {


    boolean existsByEmail(String email);
}
