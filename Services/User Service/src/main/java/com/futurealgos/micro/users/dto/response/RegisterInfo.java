/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.PreRegister;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RegisterInfo {

    public String preId;

    public String name;

    public String email;

    public String country;

    public String phoneNo;

    public RegisterInfo(String preId,
                        String name,
                        String email,
                        String country,
                        String phoneNo) {
        this.preId = preId;
        this.name = name;
        this.email = email;
        this.country = country;
        this.phoneNo = phoneNo;
    }

    public static RegisterInfo fromPreRegister(PreRegister preRegister) {
        return new RegisterInfo(
                preRegister.getId().toHexString(),
                preRegister.getName(),
                preRegister.getEmail(),
                preRegister.getCountry(),
                preRegister.getPhoneNo()
        );
    }
}
