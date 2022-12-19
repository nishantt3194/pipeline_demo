/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.repos;



import com.futurealgos.micro.credits.model.User;
import com.futurealgos.micro.credits.model.UserTransactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserTransactionRepository extends BaseRepository<UserTransactions> {

    @Query("select c from Credit c where c.user.identifier= :id order by c.createdOn DESC ")
    List<UserTransactions> findBothTransaction(@Param("id") ObjectId id);

    List<UserTransactions> findByAssociatedId(String id);
}

