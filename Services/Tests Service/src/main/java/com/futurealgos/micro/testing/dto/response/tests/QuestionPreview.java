/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.Question;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.testing.dto.response.tests
 * Project: Prasad Psycho
 * Created On: 19/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
public class QuestionPreview {

    public String num;
    public Integer index;
    public String description;

    public Set<OptionPreview> options;

    public static QuestionPreview build(Question question) {
        return QuestionPreview.builder()
                .num(question.getQuestionNumber())
                .index(question.getIndex())
                .description(question.getDescription())
                .options(question.getOptions().stream()
                        .map(OptionPreview::build).collect(Collectors.toSet()))
                .build();
    }

}
