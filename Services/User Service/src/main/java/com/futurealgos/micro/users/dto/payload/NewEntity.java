/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewEntity {

    @NotNull
    @NotBlank
    public String name;

    public String description;

    public NewEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
