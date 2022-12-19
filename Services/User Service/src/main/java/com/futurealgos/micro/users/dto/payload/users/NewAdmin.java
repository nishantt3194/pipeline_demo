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
import javax.validation.constraints.Pattern;

public class NewAdmin {

    @Email
    @NotNull
    @NotBlank
    public final String email;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    public final String firstname;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    public final String lastname;

    public NewAdmin(String email,
                    String firstname,
                    String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
