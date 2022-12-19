/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.dto.payload.StatusUpdate;
import com.futurealgos.micro.users.dto.payload.users.NewAssessor;
import com.futurealgos.micro.users.dto.payload.users.NewPartner;
import com.futurealgos.micro.users.dto.response.MinimalPartner;
import com.futurealgos.micro.users.dto.response.MinimalUser;
import com.futurealgos.micro.users.dto.response.PartnerInfo;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.models.base.Partner;
import com.futurealgos.micro.users.models.base.User;
import com.futurealgos.micro.users.models.embedded.Address;
import com.futurealgos.micro.users.repo.PartnerRepo;
import com.futurealgos.micro.users.utils.enums.PartnerType;
import com.futurealgos.micro.users.utils.enums.Purpose;
import com.futurealgos.micro.users.utils.specs.services.FetcherSpec;
import com.futurealgos.micro.users.utils.specs.services.InfoSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Package: com.futurealgos.micro.users.service
 * Project: Prasad Psycho
 * Created On: 17/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class PartnerService implements FetcherSpec<Partner>, InfoSpec<Partner, PartnerInfo> {

    @Autowired
    PartnerRepo partnerRepo;

    @Autowired
    UserService userService;

    @Override
    public Partner fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Partner fetch(ObjectId identifier) {
        return partnerRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find Partner with ID " + identifier.toHexString()));
    }

    @Override
    public List<Partner> fetchAll(List<String> identifier) {
        List<Partner> result = new ArrayList<>();
        partnerRepo.findAllById(identifier.stream().map(ObjectId::new)
                .collect(Collectors.toList())).forEach(result::add);

        return result;
    }

    public Partner register(NewPartner payload) {
        Partner partner = Partner.builder()
                .name(payload.name)
                .status(Partner.Status.PENDING)
                .type(PartnerType.valueOf(payload.partnerType.toUpperCase()))
                .purpose(payload.purpose.stream()
                        .map(String::toUpperCase)
                        .map(Purpose::valueOf).collect(Collectors.toList()))
                .adminDesignation(payload.adminDesignation)
                .adminQualification(payload.adminQualification)
                .phoneNo(payload.phoneNo)
                .extn(payload.extn)
                .address(Address.builder()
                        .line1(payload.line1)
                        .city(payload.city)
                        .state(payload.state)
                        .country(payload.country)
                        .zipcode(payload.zipcode)
                        .build())
                .build();

        User admin = userService.createPartnerAdmin(payload);
        partner.setAdministrators(new HashSet<>(List.of(admin)));
        partner = partnerRepo.save(partner, admin.getId().toHexString());
        admin.setPartner(partner);
        userService.update(admin, admin.getId().toHexString());

        return partner;
    }


    @Override
    public PartnerInfo info(String identifier) {
        return PartnerInfo.from(fetch(identifier));
    }

    public Page<MinimalPartner> list(Pageable pageable, Partner.Status status) {
        if(status == null) return partnerRepo.findAll(pageable)
                .map(MinimalPartner::from);

        return partnerRepo.findAllByStatusIs(pageable, status)
                .map(MinimalPartner::from);
    }

    public List<MinimalUser> fetchAssessors(String partnerId) {
        Partner partner = fetch(partnerId);
        return partner.getAssessors().stream()
                .map(MinimalUser::from).collect(Collectors.toList());
    }

    public List<MinimalUser> fetchAdministrators(String partnerId) {
        Partner partner = fetch(partnerId);
        return partner.getAdministrators().stream()
                .map(MinimalUser::from).collect(Collectors.toList());
    }

    public void addAssessor(NewAssessor payload, String admin) {
        Partner partner = fetch(payload.partner());
        User accessor = userService.createAccessor(payload, admin);
        accessor.setPartner(partner);
        accessor = userService.update(accessor, accessor.getId().toHexString());
        partner.getAssessors().add(accessor);
        partnerRepo.save(partner, admin);
    }

    public PartnerInfo updateStatus(StatusUpdate payload, String admin) {
        Partner partner = fetch(payload.id);
        if (!partner.getStatus().equals(Partner.Status.PENDING)) {
            partner.setStatus(payload.status ? Partner.Status.ACTIVE : Partner.Status.INACTIVE);
        }

        partner = partnerRepo.save(partner, admin);

        return PartnerInfo.from(partner);
    }

    public PartnerInfo approve(String payload, String admin) {
        Partner partner = fetch(payload);

        if (partner.getStatus().equals(Partner.Status.PENDING)) {
            partner.setStatus(Partner.Status.ACTIVE);
        }

        partner = partnerRepo.save(partner, admin);
        return PartnerInfo.from(partner);
    }
}
