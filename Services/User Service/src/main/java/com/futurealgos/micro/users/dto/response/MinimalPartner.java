/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.Partner;
import com.futurealgos.micro.users.utils.enums.Purpose;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.users.dto.response
 * Project: Prasad Psycho
 * Created On: 14/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Builder
public class MinimalPartner implements Serializable {

    public String identifier;

    public String email;

    private String adminName;

    private String status;

    public String partnerName;

    public Double credits;

    public String type;

    public String purpose;

    public String adminQualification;

    public String adminDesignation;

    public String phoneNo;

    public int totalAdministrators;

    public int totalAssessors;

    public static MinimalPartner from(Partner partner) {
        Assert.notNull(partner, "Partner Partner cannot be null");
        return MinimalPartner.builder()
                .identifier(partner.getId().toHexString())
                .email(partner.getPrimaryAdministrator().getUsername())
                .status(partner.getStatus().name())
                .adminName(partner.getPrimaryAdministrator().getFullName())
                .partnerName(partner.getName())
                .credits(partner.getCredits())
                .type(partner.getType().name())
                .purpose(partner.getPurpose().stream().map(Purpose::name).collect(Collectors.joining(",")))
                .adminQualification(partner.getAdminQualification())
                .adminDesignation(partner.getAdminDesignation())
                .phoneNo("(" + partner.getExtn() + ") " + partner.getPhoneNo())
                .totalAssessors(partner.getAssessors().size())
                .totalAdministrators(partner.getAdministrators().size())
                .build();
    }
}
