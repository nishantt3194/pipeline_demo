/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.exec;

import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.utils.enums.TimingType;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
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
public class TestPreview implements Serializable {
    public String id;
    public String testID;
    public TimingType timing;
    public Integer totalTime;
    public String description;
    public String instructions;
    public Set<QuestionDataRef> questions;

    public static TestPreview build(Test test) {
        return TestPreview.builder()
                .id(test.getId().toHexString())
                .testID(test.getTestId())
                .timing(test.getTestType())
                .totalTime(test.getTotalTime())
                .description(test.getDescription())
                .instructions(test.getInstructions())
                .questions(test.getQuestions().stream()
                        .map(QuestionDataRef::build).collect(Collectors.toSet()))
                .build();
    }

}
