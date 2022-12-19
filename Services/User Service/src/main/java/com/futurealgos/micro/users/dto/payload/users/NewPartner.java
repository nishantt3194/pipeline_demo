/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class NewPartner {

    @NotNull
    public String preId;

    @NotNull
    public String adminFirstName;

    @NotNull
    public String adminLastName;

    @NotNull
    public String adminQualification;

    @NotNull
    public String adminDesignation;

    @NotNull
    public String line1;

    @NotNull
    public String city;

    @NotNull
    public String state;

    @NotNull
    public String country;

    @NotNull
    public String zipcode;

    @NotNull
    public String extn;

    @NotNull
    public String phoneNo;

    @NotNull
    @Email
    public String email;

    @NotNull
    public String password;

    @NotNull
    public String name;

    @NotNull
    public String partnerType;

    public List<String> purpose;

}
