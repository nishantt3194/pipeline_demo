/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.repos;


import com.futurealgos.micro.documents.dao.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@NoRepositoryBean
public interface BaseRepo<T extends BaseEntity> extends MongoRepository<T, ObjectId> {


    @Transactional
    default T save(T t, String admin) {
        if (t.getId() != null) {
            t.setUpdatedBy(admin);
        } else {
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
