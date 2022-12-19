/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.FamilyType;
import com.futurealgos.micro.assessments.utils.enums.Gender;
import com.futurealgos.micro.assessments.utils.enums.MaritalStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "examinee")
public class Examinee extends BaseEntity implements Serializable {

    @Field(name = "name")
    private String name;

    @Field(name = "client_id")
    private String clientId;

    @Field(name = "email")
    private String email;

    @Field(name = "extn")
    private String extn;

    @Field(name = "phone")
    private String phone;

    @Field(name = "dob")
    private LocalDate dob;

    @Field(name = "address")
    private String address;

    @Field(name = "gender")
    private Gender gender;

    @Field(name = "mother_tongue")
    private String language;

    @Field(name = "household_income")
    private Double income;

    @Field(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Field(name = "family")
    private FamilyType family;

    @Field(name = "profession")
    private String profession;

    @Field(name = "designation")
    private String designation;

    @Field(name = "organization")
    private String organization;

    @Field(name = "comments")
    private String comments;

    @Field(name = "partner")
    @DocumentReference(collection = "partners")
    private Partner partner;

    @Field(name = "groups")
    @Builder.Default
    @DocumentReference(collection = "examinee_group")
    private List<Group> groups = new ArrayList<>();

    public String getContact() {
        if(extn == null || phone == null) {
            return "--";
        }
        return extn + "-" + phone;
    }
}
