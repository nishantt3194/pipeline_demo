/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StatusUpdate {

    @NotNull
    @NotBlank
    public String id;

    @NotNull
    public boolean status;
}
