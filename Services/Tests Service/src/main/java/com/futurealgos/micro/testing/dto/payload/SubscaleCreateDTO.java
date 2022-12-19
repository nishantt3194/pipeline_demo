/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class SubscaleCreateDTO implements Serializable {
    private static final long serialVersionUID = 4413578745505799245L;
    @NotNull
    @NotBlank
//    @ApiModelProperty(notes="subscale name",name="name", value="algebra")
    private String name;

}
