/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.users;


import com.futurealgos.micro.users.utils.enums.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

public class NewUser implements Serializable {

    private static final long serialVersionUID = 786341594292671981L;

    @NotNull
    @NotBlank
    public String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    public String firstname;


    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    public String lastname;

    @Pattern(regexp = "\\+?\\d[\\d -]{8,12}\\d")
    public String contactNo;
    public String address;

    public List<String> role;

    @NotNull
    public Status status;

    @NotNull
    public String password;


    public List<String> group;

    public NewUser(String username, String firstname, String lastname, String contactNo, String address, List<String> role, Status status, List<String> group) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contactNo = contactNo;
        this.address = address;
        this.role = role;
        this.status = status;
        this.group = group;
    }
}
