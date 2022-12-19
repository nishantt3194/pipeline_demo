/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@SuperBuilder
public class ExamineeInfo extends InfoResponse<Examinee, ExamineeInfo> {
    public String id;
    public String name;
    public String client;
    public String email;
    public String extn;
    public String phone;
    public String dob;
    public String gender;
    public String language;
    public Double income;
    public String maritalStatus;
    public String familyType;
    public String profession;
    public String designation;
    public String organization;
    public String address;
    public String comments;

    @Setter
    public List<ARMinimal> assessments;

    public static ExamineeInfo from(Examinee examinee) {
        return ExamineeInfo.builder()
                .id(examinee.getId().toHexString())
                .name(examinee.getName())
                .client(examinee.getClientId())
                .email(examinee.getEmail())
                .extn(examinee.getExtn())
                .phone(examinee.getPhone())
                .dob(DateTimeFormatter.ofPattern("dd MMM yyyy").format(examinee.getDob()))
                .gender(examinee.getGender().name())
                .language(examinee.getLanguage())
                .income(examinee.getIncome())
                .maritalStatus(examinee.getMaritalStatus().name())
                .familyType(examinee.getFamily().name())
                .profession(examinee.getProfession())
                .designation(examinee.getDesignation())
                .organization(examinee.getOrganization())
                .comments(examinee.getComments())
                .address(examinee.getAddress())
                .build();
    }
}
