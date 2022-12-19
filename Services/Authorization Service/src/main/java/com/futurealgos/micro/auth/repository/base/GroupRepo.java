/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.repository.base;


import com.futurealgos.micro.auth.models.base.Group;
import com.futurealgos.micro.auth.repository.root.BaseRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends BaseRepo<Group> {

}

