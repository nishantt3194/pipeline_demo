/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.StagedTest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
public class SavedTestsMinimal {

    private String testId;

    private String testName;

    private String createdBy;

    private String createdOn;
    private String lastModifiedOn;

    public static SavedTestsMinimal build(StagedTest stagedTest) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd hh:mm a");
        return SavedTestsMinimal.builder()
                .testId(stagedTest.getId())
                .testName(stagedTest.getTestName())
                .createdOn(df.format(stagedTest.getCreatedOn()))
                .lastModifiedOn(stagedTest.getModifiedOn() != null ? df.format(stagedTest.getModifiedOn()) : "--")
                .createdBy(stagedTest.getCreatedBy())
                .build();
    }
}
