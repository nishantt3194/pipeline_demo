/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.base.Test;
import com.futurealgos.micro.testing.repos.base.BaseRepository;
import com.futurealgos.micro.testing.repos.base.ListBuilderRepository;
import com.futurealgos.micro.testing.utils.enums.TestStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends BaseRepository<Test>, ListBuilderRepository<Test> {

    Page<Test> findByStatus(TestStatus status, Pageable pageable);

    Page<Test> findByStatusAndIdNotIn(TestStatus status, Pageable pageable, List<ObjectId> ids);

    Optional<Test> findByTestId(String id);
}
