/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.Group;
import com.futurealgos.micro.assessments.models.base.Partner;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Package: com.futurealgos.micro.assessments.repos
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@Repository
public interface GroupRepo extends BaseRepository<Group> {
    Page<Group> findAllByPartner(Partner partner, Pageable pageable);
}
