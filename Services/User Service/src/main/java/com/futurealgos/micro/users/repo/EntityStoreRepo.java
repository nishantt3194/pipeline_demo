/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;

import com.futurealgos.micro.users.models.base.EntityStore;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EntityStoreRepo extends MongoRepository<EntityStore, ObjectId> {

    EntityStore findById(UUID uuid);

    boolean existsByEntity(String name);
}
