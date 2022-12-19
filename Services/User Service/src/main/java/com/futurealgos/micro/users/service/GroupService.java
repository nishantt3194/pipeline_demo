/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.dto.payload.groups.GroupRole;
import com.futurealgos.micro.users.dto.payload.groups.NewGroup;
import com.futurealgos.micro.users.dto.response.GroupInfo;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.exceptions.exceptions.UnauthorizedException;
import com.futurealgos.micro.users.models.base.Group;
import com.futurealgos.micro.users.models.base.Role;
import com.futurealgos.micro.users.repo.GroupRepos;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepos groupRepos;
    @Autowired
    ModelMapper mapper;
    @Autowired
    RoleService roleService;

    public void createGroup(NewGroup payload) {
        boolean exists = groupRepos.existsByGroupName(payload.groupName);

        if (exists)
            throw new UnauthorizedException("Group already exists with this name");

        Group group = mapper.map(payload, Group.class);
        group.setGroupName(payload.groupName);
        group.setDescription(payload.description);
        List<Role> roles = roleService.fetch(payload.role);
        if (roles.isEmpty()) {
            throw new UnauthorizedException("One of the role is incorrect");
        }
        group.setRoles(roles);
        group = groupRepos.save(group);


    }

    public Group getGroup(String groupName) {
        return groupRepos.findByGroupName(groupName)
                .orElseThrow(() -> new NotFoundException("Could not find Group with name: " + groupName));

    }

    public List<GroupInfo> getAllUser() throws NotFoundException {
        return groupRepos.findAll().stream().map(GroupInfo::fromGroup).toList();
    }

    public GroupInfo getGroupInfo(String groupName) {
        return GroupInfo.fromGroup(getGroup(groupName));
    }


    public void addRoleToGroup(GroupRole payload) {
        Group group = getGroup(payload.getGroupName());

        List<Role> roles = roleService.fetch(payload.getRoles());

        if (roles.size() != payload.getRoles().size()) {
            throw new InputMismatchException("could not find role name: " + roles);
        }
        if (!Collections.disjoint(group.getRoles(), roles)) {
            throw new UnauthorizedException("One of the role already exists");
        }
        group.getRoles().addAll(roles);
        groupRepos.save(group);
    }


    public List<Group> fetchGroupData(List<String> names) {
        return groupRepos.findByGroupNameIn(names);
    }
}


