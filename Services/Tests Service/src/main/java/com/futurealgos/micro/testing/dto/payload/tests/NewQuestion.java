/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.models.base.Question;
import com.futurealgos.micro.testing.utils.enums.LikertScoring;
import com.futurealgos.micro.testing.utils.enums.QuestionType;

import java.util.List;

public record NewQuestion(
        String id,
        String number,
        Integer index,
        String description,
        String subscale,
        QuestionType type,
        LikertScoring scoring,
        String tag,
        List<NewOption> options) {

    public Question convert() {
        return shell().build();
    }

    public Question.QuestionBuilder shell() {
        return Question.builder()
                .questionNumber(number)
                .index(index)
                .type(type)
                .likertScoring(scoring)
                .description(description);
    }


}
