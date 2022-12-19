/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Package: com.futurealgos.micro.assessments.dto.payload
 * Project: Prasad Psycho
 * Created On: 04/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record ExecAnswer(
        @NotNull @NotEmpty String id,
        Integer answer,
        Boolean clear) {
}
