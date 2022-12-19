/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import com.futurealgos.micro.assessments.repos.base.ListBuilderRepository;
import com.futurealgos.micro.assessments.utils.enums.TestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends BaseRepository<Test>, ListBuilderRepository<Test> {

    Page<Test> findByStatus(TestStatus status, Pageable pageable);

    Optional<Test> findByTestId(String id);
}
