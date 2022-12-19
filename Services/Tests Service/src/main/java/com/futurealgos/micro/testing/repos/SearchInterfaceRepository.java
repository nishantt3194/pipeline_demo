/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchInterfaceRepository<T extends BaseEntity> extends MongoRepository<T, ObjectId> {

//      Example<T> example= Example.of(new T());

    @Query("db.?tableName.find({?findBy  : { $eq: ?value } })")
    List<T> find(@Param("tableName") String tableName, @Param("findBy") String findBy, @Param("value") String value);

}
