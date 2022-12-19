/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class QuestionCreationDTO implements Serializable {

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "options",name="options",value="options")
    public List<String> getOptionDataId;
    @NotBlank
    @NotNull
//    @ApiModelProperty(notes = "Question Number",name="questionNumber",value="1")
    private Integer questionNumber;
    @NotBlank
    @NotNull
//    @ApiModelProperty(notes = "Question Type",name="questionType",value="MCQ")
    private String questionType;
    @NotBlank
    @NotNull
//    @ApiModelProperty(notes = "Questions Likert Scoring",name="likertScoring",value="REVERSE")
    private String likertScoring;
    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "Questions Description",name="questionText",value="questionText")
    private String questionText;
    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "Questions Image Url",name="questionImage",value="questionImage")
    private String questionImage;
}
