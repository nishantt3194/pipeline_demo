/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


@Getter
@Setter
@Builder
public class Address implements Serializable {

    @Field
    private String line1;

    @Field
    private String city;
    @Field
    private String state;

    @Field
    private String country;

    @Field
    private String zipcode;

}
