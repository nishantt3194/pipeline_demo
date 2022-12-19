/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;


import com.futurealgos.micro.testing.models.base.Question;
import com.futurealgos.micro.testing.utils.enums.QuestionType;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;


@Getter
@Builder
public class QuestionInfo implements Serializable {
    private String num;
    private Integer index;
    private String description;
    private String subscale;
    private String type;
    private String scoring;

    private List<OptionInfo> options;


    public static QuestionInfo build(Question question) {
        return QuestionInfo.builder()
                .num(question.getQuestionNumber())
                .index(question.getIndex())
                .description(question.getDescription())
                .subscale(question.getSubscale().getName())
                .type(question.getType().getName())
                .scoring(question.getType().equals(QuestionType.LIKERT) ? question.getLikertScoring().getName() : "--")
                .options(question.getOptions().stream()
                        .map(OptionInfo::build).toList())
                .build();
    }
}
