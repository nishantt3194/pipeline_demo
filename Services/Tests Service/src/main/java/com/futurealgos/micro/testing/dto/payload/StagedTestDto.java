/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;

import com.futurealgos.micro.testing.dto.payload.tests.NewQuestion;
import com.futurealgos.micro.testing.dto.payload.tests.NewSubscale;
import com.futurealgos.micro.testing.models.embedded.QueuedObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@Getter
@Setter
public class StagedTestDto {
    public String id;

    public String name;

    public String description;

    public String instructions;

    public String category;

    public String accessLevel;

    public String administrationType;

    public List<Integer> age;

    public Integer minAgeMonth;

    public Integer maxAgeMonth;


    public Integer minAgeYear;

    public Integer maxAgeYear;

    public String testType;

    public Integer totalTime;

    public String mode;

    public Integer credits;

    public List<String> templates;

    public String language;

    public List<String> norms;

    public List<NewQuestion> questions;
    public List<NewSubscale> subscales;


    public NewSubscale defaultSubscale;
    public Boolean submitted;

    public List<QueuedObject> queue;

    public String introduction;

    public String author;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("testName", name)
                .append("description", description)
                .append("instructions", instructions)
                .append("category", category)
                .append("accessLevel", accessLevel)
                .append("administrationType", administrationType)
                .append("age", age)
                .append("minAgeMonth", minAgeMonth)
                .append("maxAgeMonth", maxAgeMonth)
                .append("minAgeYear", minAgeYear)
                .append("maxAgeYear", maxAgeYear)
                .append("testType", testType)
                .append("totalTime", totalTime)
                .append("mode", mode)
                .append("credits", credits)
                .append("templates", templates)
                .append("language", language)
                .append("norms", norms)
                .append("questions", questions)
                .append("subscales", subscales)
                .append("defaultSubscale", defaultSubscale)
                .append("submitted", submitted)
                .append("queue", queue)
                .toString();
    }
}
