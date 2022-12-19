/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.utils.enums.InterpretationType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public record NewInterpretation(
        @NotNull @NotEmpty String testId,
        @NotNull @NotEmpty String name,
        @NotNull InterpretationType type,
        String description) {


}
