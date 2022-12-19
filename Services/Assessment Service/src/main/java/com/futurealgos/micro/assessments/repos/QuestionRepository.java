/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.Question;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import com.futurealgos.micro.assessments.repos.base.ListBuilderRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends BaseRepository<Question>, ListBuilderRepository<Question> {


    Question findByQuestionNumber(String num);
}
