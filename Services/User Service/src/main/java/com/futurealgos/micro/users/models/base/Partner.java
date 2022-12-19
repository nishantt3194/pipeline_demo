/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.embedded.Address;
import com.futurealgos.micro.users.models.root.BaseEntity;
import com.futurealgos.micro.users.utils.enums.PartnerType;
import com.futurealgos.micro.users.utils.enums.Purpose;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "partners")
public class Partner extends BaseEntity implements Serializable {

    public enum Status {
        PENDING, ACTIVE, INACTIVE;
    }

    @Field("status")
    private Status status;

    @Field
    private Address address;

    @Field
    private Double credits;

    @Field("name")
    private String name;

    @Field("admin_qualifications")
    private String adminQualification;

    @Field("partner_type")
    private PartnerType type;

    @Field("phone_number")
    private String phoneNo;

    @Field
    private String extn;

    @Field("admin_designation")
    private String adminDesignation;

    @Field
    private List<Purpose> purpose;

    @Field
    @Builder.Default
    @DocumentReference(collection = "document_requests")
    private Set<DocRequest> requests = new HashSet<>();

    enum ACLType {
        WHITELISTED, BLACKLISTED
    }

    @Field("acl_type")
    public ACLType aclType;

    @Field("acl_status")
    public boolean aclStatus;

    @Field("acl_tests")
    @Builder.Default
    @DocumentReference(collection = "test", lazy = true)
    private Set<Test> aclTests = new HashSet<>();

    @Field("primary_administrator")
    @DocumentReference(collection = "users", lazy = true)
    private User primaryAdministrator;

    @Field("administrators")
    @Builder.Default
    @DocumentReference(collection = "users", lazy = true)
    private Set<User> administrators = new HashSet<>();

    @Field("assessors")
    @Builder.Default
    @DocumentReference(collection = "users", lazy = true)
    private Set<User> assessors = new HashSet<>();


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("status", status)
                .append("address", address)
                .append("credits", credits)
                .append("name", name)
                .append("adminQualification", adminQualification)
                .append("type", type)
                .append("phoneNo", phoneNo)
                .append("extn", extn)
                .append("adminDesignation", adminDesignation)
                .append("purpose", purpose)
                .append("requests", requests)
                .append("aclType", aclType)
                .append("aclStatus", aclStatus)
                .append("aclTests", aclTests)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .append("createdBy", createdBy)
                .append("updatedBy", updatedBy)
                .append("version", version)
                .toString();
    }
}
