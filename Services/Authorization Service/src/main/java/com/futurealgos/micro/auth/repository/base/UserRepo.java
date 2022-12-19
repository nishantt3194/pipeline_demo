/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.repository.base;

import com.futurealgos.micro.auth.models.base.User;
import com.futurealgos.micro.auth.repository.root.BaseRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends BaseRepo<User> {
    Optional<User> findByUsername(String username);

}
