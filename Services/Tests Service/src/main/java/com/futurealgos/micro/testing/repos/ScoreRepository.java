/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.embedded.Score;
import com.futurealgos.micro.testing.repos.base.BaseRepository;
import com.futurealgos.micro.testing.repos.base.ListBuilderRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends BaseRepository<Score>, ListBuilderRepository<Score> {
}
