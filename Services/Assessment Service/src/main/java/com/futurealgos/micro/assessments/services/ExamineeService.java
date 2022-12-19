/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.NewExaminee;
import com.futurealgos.micro.assessments.dto.response.admin.ARMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeInfo;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.ExamineeSearch;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.models.base.Partner;
import com.futurealgos.micro.assessments.repos.ARRepo;
import com.futurealgos.micro.assessments.repos.AssigneeRepo;
import com.futurealgos.micro.assessments.repos.ExamineeRepo;
import com.futurealgos.micro.assessments.utils.enums.FamilyType;
import com.futurealgos.micro.assessments.utils.enums.Gender;
import com.futurealgos.micro.assessments.utils.enums.MaritalStatus;
import com.futurealgos.micro.assessments.utils.specs.services.CreateSpec;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import com.futurealgos.micro.assessments.utils.specs.services.InfoSpec;
import com.futurealgos.micro.assessments.utils.specs.services.InternalUpdateSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class ExamineeService implements FetcherSpec<Examinee>, InfoSpec<Examinee, ExamineeInfo>,
        CreateSpec<Examinee, NewExaminee>, InternalUpdateSpec<Examinee> {

    @Autowired
    ExamineeRepo examineeRepo;

    @Autowired
    ARRepo assessmentRepo;

    @Autowired
    AssigneeRepo assigneeRepo;

    @Autowired
    PartnerService partnerService;

    @Override
    public Examinee fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Examinee fetch(ObjectId identifier) {
        return examineeRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find examinee with given ID " + identifier.toHexString()));
    }

    @Override
    public List<Examinee> fetchAll(List<String> identifier) {
        List<Examinee> result = new ArrayList<>();
        examineeRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    @Override
    public Examinee create(NewExaminee payload, String admin) {
        Partner partner = partnerService.fetch(payload.partner());

        Examinee examinee = Examinee.builder()
                .name(payload.name())
                .clientId(payload.clientId())
                .partner(partner)
                .email(payload.email())
                .phone(payload.phone())
                .dob(LocalDate.parse(payload.dob(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .gender(Gender.valueOf(payload.gender()))
                .language(payload.language())
                .income(payload.income())
                .maritalStatus(MaritalStatus.valueOf(payload.maritalStatus()))
                .family(FamilyType.valueOf(payload.familyType()))
                .profession(payload.profession())
                .designation(payload.designation())
                .organization(payload.organization())
                .comments(payload.comments())
                .build();
        return examineeRepo.save(examinee);
    }

    @Override
    public ExamineeInfo info(String identifier) {
        Examinee examinee = fetch(identifier);
        ExamineeInfo info = ExamineeInfo.from(examinee);
        info.setAssessments(assigneeRepo.findByExaminee(examinee).stream()
                .map(ARMinimal::convert).toList());
        return info;
    }

    public List<ExamineeSearch> search(String partnerId) {
        Partner partner = partnerService.fetch(partnerId);
        return examineeRepo.findAllByPartner(partner, Pageable.unpaged()).stream()
                .map(ExamineeSearch::from).toList();

    }

    public Page<ExamineeMinimal> list(String partnerId, Pageable pageable) {
        Partner partner = partnerService.fetch(partnerId);
        return examineeRepo.findAllByPartner(partner, pageable).map(ExamineeMinimal::convert);
    }

    public List<ARMinimal> listOfAssessments(String examineeId) {
        List<Assignee> assignees = assigneeRepo.findByExaminee(fetch(examineeId));
        return assignees.stream().map(Assignee::getRequest).map(ARMinimal::convert).toList();
    }

    @Override
    public Examinee update(Examinee payload, String admin) {
        return examineeRepo.save(payload, admin);
    }
}
