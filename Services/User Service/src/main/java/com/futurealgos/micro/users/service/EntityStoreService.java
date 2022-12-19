/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.dto.payload.NewEntity;
import com.futurealgos.micro.users.exceptions.exceptions.UnauthorizedException;
import com.futurealgos.micro.users.models.base.EntityStore;
import com.futurealgos.micro.users.repo.EntityStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityStoreService {

    @Autowired
    EntityStoreRepo entityStoreRepo;


    public void create(NewEntity payload) {
        boolean exists = entityStoreRepo.existsByEntity(payload.name);
        if (exists) {
            throw new UnauthorizedException("Entity name Already exists");
        }

        EntityStore store = new EntityStore();
        store.setEntity(payload.name);
        store.setDescription(payload.description);
        entityStoreRepo.save(store);
    }

    public List<EntityStore> findAll() {
        return entityStoreRepo.findAll();
    }


}
