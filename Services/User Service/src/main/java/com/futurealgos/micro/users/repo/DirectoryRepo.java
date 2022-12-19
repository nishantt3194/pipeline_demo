/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.repo;

import com.futurealgos.micro.users.models.base.AuthDirectory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Package: com.futurealgos.micro.users.repo
 * Project: Prasad Psycho
 * Created On: 21/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Repository
public interface DirectoryRepo extends MongoRepository<AuthDirectory, ObjectId> {


    Optional<AuthDirectory> findByName(String name);
}
