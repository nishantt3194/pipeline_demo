/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;

import com.futurealgos.micro.users.models.base.Group;
import com.futurealgos.micro.users.models.base.Partner;
import com.futurealgos.micro.users.models.base.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepo extends BaseRepo<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String name);
    Page<User> findUsersByPrimaryGroupAndDetails_PartnerStatus(Group partner, Partner.Status status, Pageable pageable);


}
