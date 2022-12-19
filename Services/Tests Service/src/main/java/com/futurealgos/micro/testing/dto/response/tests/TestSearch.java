/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.utils.specs.dto.SearchResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Package: com.futurealgos.micro.testing.dto.response.tests
 * Project: Prasad Psycho
 * Created On: 02/09/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@SuperBuilder
@Getter
public class TestSearch extends SearchResponse {

    public String testId;
    public Double credits;

    public static TestSearch from(TestsMinimal minimal) {
        return TestSearch.builder()
                .label(minimal.getTestName())
                .identifier(minimal.getIdentifier())
                .testId(minimal.getTestId())
                .credits(minimal.getCredits())
                .build();
    }

}
