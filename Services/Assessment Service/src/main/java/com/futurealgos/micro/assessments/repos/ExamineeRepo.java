/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos;

import com.futurealgos.micro.assessments.models.base.Examinee;
import com.futurealgos.micro.assessments.models.base.Partner;
import com.futurealgos.micro.assessments.repos.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExamineeRepo extends BaseRepository<Examinee> {

    Page<Examinee> findAllByPartner(Partner partner, Pageable pageable);
}
