/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.AssessmentRequest;
import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.repos
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Repository
public interface ARRepo extends BaseRepository<AssessmentRequest> {
    List<AssessmentRequest> findByAssigneesContains(Assignee assignee);
}
