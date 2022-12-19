/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.exec;

import com.futurealgos.micro.assessments.models.base.Question;

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
public class QuestionDataRef {

    public String id;
    public String num;
    public Integer index;
    public String description;
    public String subscaleId;

    public String subscaleName;

    public Set<OptionDataRef> options;

    public static QuestionDataRef build(Question question) {
        return QuestionDataRef.builder()
                .id(question.getId().toHexString())
                .num(question.getQuestionNumber())
                .index(question.getIndex())
                .subscaleId(question.getSubscale()!= null ? question.getSubscale().getId().toHexString() : null)
                .subscaleName(question.getSubscale()!= null ? question.getSubscale().getName() : null)
                .description(question.getDescription())
                .options(question.getOptions().stream()
                        .map(OptionDataRef::build).collect(Collectors.toSet()))
                .build();
    }

}
