/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.base.DetailedInterpretation;
import com.futurealgos.micro.testing.repos.base.BaseRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailedInterpretationRepository extends BaseRepository<DetailedInterpretation> {

    List<DetailedInterpretation> findByTest_Id(ObjectId id);
}
