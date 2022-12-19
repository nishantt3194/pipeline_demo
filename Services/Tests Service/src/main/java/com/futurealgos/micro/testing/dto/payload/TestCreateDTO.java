/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCreateDTO implements Serializable {

    private static final long serialVersionUID = 5306114610008068247L;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "Test id",name="testId",value="101")
    private String testId;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "Test Name",name="name",value="intelligence test")
    private String name;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "introduction",name="introduction",value="introduction")
    private String introduction;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "testCategoryId",name="testCategoryId",value="testCategoryId")
    private String testCategoryId;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "accessLevel",name="accessLevel",value="A")
    private String accessLevel;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "administrationType",name="administrationType",value="GROUP")
    private String administrationType;

    @NotNull
//    @ApiModelProperty(notes = "minimum age",name="age",value="15")
    private int minAge;

    @NotNull
//    @ApiModelProperty(notes = "maximum age",name="maxAge",value="30")
    private int maxAge;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "testType",name="testType",value="testType")
    private String testType;

    @NotNull
//    @ApiModelProperty(notes = "time in minutes",name="timeInMinutes",value="90")
    private Long timeInMinutes;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "instructions",name="instructions",value="instructions")
    private String instructions;

    @NotNull
    @NotBlank
    private String author;

    @NotNull
    @NotBlank
//    @ApiModelProperty(notes = "logo Image",name="logoImage",value="logoImage")
    private String logoImage;

    @NotNull
//    @ApiModelProperty(notes = "subscale ids",name="subscaleIds",value="11")
    private List<String> subscaleIds;

    @NotNull
//    @ApiModelProperty(notes = "norms",name="norms",value="gender age")
    private List<String> norms;

    @NotNull
//    @ApiModelProperty(notes = "questionIds",name="questionIds",value="12")
    private List<String> questionIds;
}
