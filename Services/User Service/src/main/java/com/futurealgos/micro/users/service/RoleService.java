/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.dto.payload.roles.NewRole;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.exceptions.exceptions.UnauthorizedException;
import com.futurealgos.micro.users.models.base.EntityStore;
import com.futurealgos.micro.users.models.base.Role;
import com.futurealgos.micro.users.repo.EntityStoreRepo;
import com.futurealgos.micro.users.repo.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Component
public class RoleService {

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ModelMapper mapper;
    @Autowired
    EntityStoreRepo entityStoreRepo;


    public void createRole(NewRole payload) {

        EntityStore store = entityStoreRepo.findById(UUID.fromString(payload.entityId));
        if (store == null) {
            throw new NotFoundException("Entity does not exists");
        }
        boolean exists = roleRepo.existsByTag(payload.tag);
        if (exists)
            throw new UnauthorizedException("Role already exists");

        Role role = mapper.map(payload, Role.class);
        role.setTag(payload.tag);
        role.setStore(store);
        role.setDescription(payload.description);

        roleRepo.save(role);


    }

    public List<Role> fetch(List<String> names) {
        return roleRepo.findByTagIn(names);
    }

    public Role fetch(String roleName) {
        return roleRepo.findByTag(roleName)
                .orElseThrow(() -> new NotFoundException("Could not find role: " + roleName));
    }

    public List<Role> getAllRoles() throws NotFoundException {
        return roleRepo.findAll();
    }


}
