/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.repos;

import com.futurealgos.micro.documents.dao.DocumentMetadata;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MetadataRepo extends BaseRepo<DocumentMetadata> {

    Optional<DocumentMetadata> findByTag(String tag);
}
