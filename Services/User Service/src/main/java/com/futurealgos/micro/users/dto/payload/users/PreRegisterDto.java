/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PreRegisterDto {
    @NotNull
    @NotBlank
    public String name;
    @NotNull
    @Email
    public String email;

    @NotNull
    @NotBlank
    public String country;

    @NotNull
    @NotBlank
    public String phoneNo;

}
