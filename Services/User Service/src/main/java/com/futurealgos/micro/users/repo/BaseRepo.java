/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;


import com.futurealgos.micro.users.models.root.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Repository
public interface BaseRepo<T extends BaseEntity> extends MongoRepository<T, ObjectId> {
    @Transactional
    default T save(T t, String admin) {
        if (t.getId() != null) {
            t.setUpdatedOn(new Date());
            t.setUpdatedBy(admin);
        } else {
            t.setCreatedOn(new Date());
            t.setUpdatedOn(new Date());
            t.setUpdatedBy(admin);
            t.setCreatedBy(admin);
        }
        return save(t);
    }

    @Transactional
    default T save(T t, String admin, Set<String> changes) {
        if (t.getId() != null) {
            t.setUpdatedBy(admin);
        } else {
            t.setUpdatedBy(admin);
            t.setCreatedBy(admin);
        }
        return save(t);
    }
}
