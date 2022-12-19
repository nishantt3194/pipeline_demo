/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.exec;

import com.futurealgos.micro.assessments.models.base.Test;
import com.futurealgos.micro.assessments.utils.enums.Mode;
import com.futurealgos.micro.assessments.utils.enums.TimingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Builder
public class TestExecData implements Serializable {

    public String id;
    public String testId;
    public String name;
    public Mode mode;

    public TimingType subscaleMode;

    public TimingType timing;
    public Long timeLimit;
    public Long initialTime;
    public Long initialSubscaleTime;
    public String currentSubscale;


    @Builder.Default
    public Set<SubscaleDataRef> subscales = new HashSet<>();
    @Builder.Default
    public Set<QuestionDataRef> questions = new HashSet<>();
    @Builder.Default
    public Map<String, Integer> answers = new HashMap<>();
    public static TestExecData build(Test test) {
        Set<SubscaleDataRef> subscaleDataRefs;
        if(test.getMode().equals(Mode.DEFAULT))
            subscaleDataRefs = new HashSet<>();

        else subscaleDataRefs = test.getSubscales().stream()
                .map(SubscaleDataRef::build).collect(Collectors.toSet());

        return TestExecData.builder()
                .id(test.getId().toHexString())
                .testId(test.getTestId())
                .name(test.getTestName())
                .mode(test.getMode())
                .subscaleMode(test.getSubscaleMode())
                .timing(test.getTestType())
                .timeLimit(Duration.ofMinutes(test.getTotalTime()).toMillis())
                .subscales(subscaleDataRefs)
                .questions(test.getQuestions().stream()
                        .map(QuestionDataRef::build).collect(Collectors.toSet()))
                .build();
    }


    public void buildAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }
}
