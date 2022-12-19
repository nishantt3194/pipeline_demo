/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;

import com.futurealgos.micro.users.models.base.Group;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepos extends BaseRepo<Group> {
    boolean existsByGroupName(String groupName);

    boolean existsByGroupNameIn(Collection<String> groupNames);

    Optional<Group> findByGroupName(String groupName);

    List<Group> findByGroupNameIn(Collection<String> groupNames);

}

