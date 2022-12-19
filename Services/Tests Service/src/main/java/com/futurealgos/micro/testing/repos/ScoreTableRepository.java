/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.base.ScoreTable;
import com.futurealgos.micro.testing.repos.base.BaseRepository;
import com.futurealgos.micro.testing.repos.base.ListBuilderRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreTableRepository extends BaseRepository<ScoreTable>, ListBuilderRepository<ScoreTable> {

    ScoreTable findBySubscale_Id(ObjectId id);
}
