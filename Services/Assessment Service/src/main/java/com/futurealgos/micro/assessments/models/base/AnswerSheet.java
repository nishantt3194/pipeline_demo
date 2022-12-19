/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Builder
@Document(collection = "answer_sheet")
public class AnswerSheet extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7490383928760500817L;

    @DocumentReference(collection = "assessment_request")
    private AssessmentRequest assessmentRequest;

    @DocumentReference(collection = "assignee")
    private Assignee assignee;

    @Field(name = "answers")
    @Builder.Default
    private Map<String,Integer> answers = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnswerSheet that = (AnswerSheet) o;
        return Objects.equals(assessmentRequest, that.assessmentRequest) && Objects.equals(assignee, that.assignee) && Objects.equals(answers, that.answers);
    }


    public void buildSheet(Test test) {
        answers.clear();
        for (Question question: test.getQuestions()) {
            answers.put(question.getId().toHexString(), null);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), assessmentRequest, assignee, answers);
    }
}
