/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.repository.root;

import com.futurealgos.micro.auth.models.root.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@NoRepositoryBean
public interface BaseRepo<T extends BaseEntity> extends MongoRepository<T, ObjectId> {

    @Transactional
    default T save(T t, String admin) {
        if (t.getId() != null) {
            t.setUpdatedBy(admin);
            t.setUpdatedOn(new Date());
        } else {
            t.setUpdatedBy(admin);
            t.setCreatedBy(admin);
            t.setUpdatedOn(new Date());
            t.setCreatedOn(new Date());
        }
        return save(t);
    }

    @Transactional
    default T save(T t, String admin, Set<String> changes) {
        if (t.getId() != null) {
            t.setUpdatedBy(admin);
            t.setUpdatedBy(admin);
            t.setUpdatedOn(new Date());
        } else {
            t.setUpdatedBy(admin);
            t.setCreatedBy(admin);
            t.setUpdatedOn(new Date());
            t.setCreatedOn(new Date());
        }
        return save(t);
    }
}
