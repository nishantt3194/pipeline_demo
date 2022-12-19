/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.roles;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
public class NewRole {


    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Z]+(?:_[A-Z]+)*$")
    public String tag;

    public String description;

    public String entityId;


    public NewRole(String tag, String description, String entityId) {
        this.tag = tag;
        this.description = description;
        this.entityId = entityId;
    }


}
