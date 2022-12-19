/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.utils.specs.services;


import com.futurealgos.micro.documents.utils.specs.dto.SearchResponse;

public interface SearchSpec<S extends SearchResponse> {

    S search();
}
