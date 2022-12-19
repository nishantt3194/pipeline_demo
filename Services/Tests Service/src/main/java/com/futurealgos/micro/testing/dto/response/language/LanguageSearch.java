/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.language;

import com.futurealgos.micro.testing.models.base.LanguageStore;
import com.futurealgos.micro.testing.utils.specs.dto.SearchResponse;

public class LanguageSearch extends SearchResponse {

    public LanguageSearch(LanguageStore languageStore) {
        super(languageStore.getId().toHexString(), languageStore.getTag());
    }
}
