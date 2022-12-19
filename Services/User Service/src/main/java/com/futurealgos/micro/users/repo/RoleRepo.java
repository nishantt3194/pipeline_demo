/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;

import com.futurealgos.micro.users.models.base.Role;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepo extends BaseRepo<Role> {
    boolean existsByTag(String roleName);

    List<Role> findByTagIn(Collection<String> roleNames);


    Optional<Role> findByTag(String roleName);
}