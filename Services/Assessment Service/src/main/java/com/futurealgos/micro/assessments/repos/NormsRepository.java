/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.Norm;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import com.futurealgos.micro.assessments.repos.base.ListBuilderRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NormsRepository extends BaseRepository<Norm>, ListBuilderRepository<Norm> {

//    List<Norm>
}
