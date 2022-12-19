/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;


import com.futurealgos.micro.assessments.models.base.ScoreTable;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import com.futurealgos.micro.assessments.repos.base.ListBuilderRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreTableRepository extends BaseRepository<ScoreTable>, ListBuilderRepository<ScoreTable> {

    ScoreTable findByName(String name);

    List<ScoreTable> findAllByName(List<String> names);
}
