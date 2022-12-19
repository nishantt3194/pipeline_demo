/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.PreRegister;
import com.futurealgos.micro.users.utils.specs.dto.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.SimpleDateFormat;

@Getter
@Setter
@SuperBuilder
public class MinRegister extends ListResponse {

    public String name;

    public String email;

    public String phoneNo;

    public String createdOn;

    public static MinRegister fromPreRegister(PreRegister preRegister) {
        return MinRegister.builder()
                .identifier(preRegister.getId().toHexString())
                .name(preRegister.getName())
                .email(preRegister.getEmail())
                .phoneNo(preRegister.getPhoneNo())
                .createdOn(new SimpleDateFormat("dd-MM-yyyy").format(preRegister.getCreatedOn()))
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("email", email)
                .append("phoneNo", phoneNo)
                .toString();
    }
}
