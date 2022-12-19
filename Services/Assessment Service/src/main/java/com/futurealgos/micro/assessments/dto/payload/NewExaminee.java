/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Package: com.futurealgos.micro.assessments.dto.payload
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record NewExaminee(
        String partner,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String clientId,
        @NotNull @NotBlank String email,
        String extn,
        @NotBlank @NotNull String phone,
        String dob,
        String gender,
        String language,
        Double income,
        String maritalStatus,
        String familyType,
        String profession,
        String designation,
        String organization,
        String comments
) {

}
