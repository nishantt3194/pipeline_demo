/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.repos;



import com.futurealgos.micro.credits.model.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;


public interface BaseRepository<T extends BaseEntity> extends MongoRepository<T, ObjectId> {


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
}
