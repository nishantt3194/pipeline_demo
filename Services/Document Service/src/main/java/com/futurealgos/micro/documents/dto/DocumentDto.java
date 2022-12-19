/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.dto;

import jakarta.validation.constraints.NotNull;

public class DocumentDto {

    @NotNull
    public String name;
    public String desc;
    @NotNull
    public String type;
    @NotNull
    public String prefix;
}
