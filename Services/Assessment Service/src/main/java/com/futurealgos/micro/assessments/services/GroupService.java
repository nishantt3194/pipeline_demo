/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.AssignGroup;
import com.futurealgos.micro.assessments.dto.payload.NewGroup;
import com.futurealgos.micro.assessments.dto.response.admin.GroupMinimal;
import com.futurealgos.micro.assessments.dto.response.admin.GroupSearch;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.models.base.Group;
import com.futurealgos.micro.assessments.models.base.Partner;
import com.futurealgos.micro.assessments.repos.GroupRepo;
import com.futurealgos.micro.assessments.utils.specs.services.CreateSpec;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
public class GroupService implements FetcherSpec<Group>, CreateSpec<Group, NewGroup> {

    @Autowired
    GroupRepo groupRepo;

    @Autowired
    ExamineeService examineeService;

    @Autowired
    PartnerService partnerService;

    @Override
    public Group fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Group fetch(ObjectId identifier) {
        return groupRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find group with given ID " + identifier.toHexString()));
    }


    public List<Examinee> getExaminees(String identifier) {
        Group group = fetch(identifier);
        return group.getExaminees();
    }

    @Override
    public List<Group> fetchAll(List<String> identifier) {
        List<Group> result = new ArrayList<>();
        groupRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    @Override
    public Group create(NewGroup payload, String admin) {
        Partner partner = partnerService.fetch(payload.partner());
        Group group = Group.convert(payload);
        group.setPartner(partner);
        return groupRepo.save(group, admin);
    }

    public Page<GroupMinimal> list(Pageable pageable, String partnerId) {
        Partner partner = partnerService.fetch(partnerId);
        return groupRepo.findAllByPartner(partner, pageable).map(GroupMinimal::convert);
    }

    public List<GroupSearch> search(String partnerId) {
        Partner partner = partnerService.fetch(partnerId);
        return groupRepo.findAllByPartner(partner, Pageable.unpaged()).stream()
                .map(GroupSearch::new).toList();
    }


    public Group assign(AssignGroup payload, String admin) {
        Group group = fetch(payload.getGroup());
        List<Examinee> examines = examineeService.fetchAll(payload.examinees);
        group.examinees.addAll(examines);
        group = groupRepo.save(group, admin);
        Group saved = group;
        examines.forEach(examinee -> {
            examinee.getGroups().add(saved);
            examineeService.update(examinee, admin);
        });

        return saved;
    }

    public Group remove(AssignGroup payload, String admin) {
        Group group = fetch(payload.getGroup());
        List<Examinee> examines = examineeService.fetchAll(payload.examinees);
        group.examinees.removeAll(examines);
        group = groupRepo.save(group, admin);
        Group saved = group;
        examines.forEach(examinee -> {
            examinee.getGroups().remove(saved);
            examineeService.update(examinee, admin);
        });

        return saved;
    }
}
