/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

public enum QuestionType {

    LIKERT("Likert", "Question Type is likert"),
    MCQ("MCQ", "Question scheduleType is Multiple choice Question");


    private String name;
    private String description;

    QuestionType(String name, String description) {

        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
