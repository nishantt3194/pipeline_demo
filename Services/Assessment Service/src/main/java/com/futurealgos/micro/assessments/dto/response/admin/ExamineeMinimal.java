/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.utils.specs.dto.ListResponse;
import lombok.experimental.SuperBuilder;


/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@SuperBuilder
public class ExamineeMinimal extends ListResponse {

    public String name;
    public String email;
    public String contact;
    public String gender;
    public String profession;
    public String organization;



    public static ExamineeMinimal convert(Examinee examinee){
        return ExamineeMinimal.builder()
                .identifier(examinee.getId().toHexString())
                .name(examinee.getName())
                .email(examinee.getEmail())
                .contact(examinee.getContact())
                .gender(examinee.getGender().getTag())

                .profession(examinee.getProfession())
                .organization(examinee.getOrganization())
                .build();
    }

}
