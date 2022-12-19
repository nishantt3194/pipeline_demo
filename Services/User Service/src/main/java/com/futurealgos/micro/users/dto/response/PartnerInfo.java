/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.Partner;
import com.futurealgos.micro.users.utils.enums.Purpose;
import com.futurealgos.micro.users.utils.specs.dto.InfoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.users.dto.response
 * Project: Prasad Psycho
 * Created On: 13/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@SuperBuilder
public class PartnerInfo extends InfoResponse<Partner> implements Serializable {
    private String partnerName;
    private String adminQualification;
    private String partnerType;
    private String phoneNo;
    private String extn;
    private String email;
    private String adminDesignation;
    private List<Purpose> purpose;
    private String status;
    private String username;
    private String firstName;
    private String lastName;
    private String line1;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private Double credits;
    private Set<RequestInfo> requests;
    private List<TransactionsHistory> history;

    public static PartnerInfo from(Partner partner) {
        return PartnerInfo.builder()
                .identifier(partner.getId().toHexString())
                .partnerName(partner.getName())
                .firstName(partner.getPrimaryAdministrator().getFirstName())
                .lastName(partner.getPrimaryAdministrator().getLastName())
                .email(partner.getPrimaryAdministrator().getUsername())
                .adminQualification(partner.getAdminQualification())
                .partnerType(partner.getType().toString())
                .phoneNo(partner.getPhoneNo())
                .extn(partner.getExtn())
                .adminDesignation(partner.getAdminDesignation())
                .purpose(partner.getPurpose())
                .status(partner.getStatus().toString())
                .line1(partner.getAddress().getLine1())
                .city(partner.getAddress().getCity())
                .state(partner.getAddress().getState())
                .country(partner.getAddress().getCountry())
                .zipcode(partner.getAddress().getZipcode())
                .credits(partner.getCredits())
                .requests(partner.getRequests().stream()
                        .map(RequestInfo::fromDocRequest).collect(Collectors.toSet()))
                .build();
    }
}
