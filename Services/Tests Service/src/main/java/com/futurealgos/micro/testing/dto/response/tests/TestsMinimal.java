/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;


import com.futurealgos.micro.testing.models.base.Test;
import com.futurealgos.micro.testing.utils.mappings.BaseMap;
import com.futurealgos.micro.testing.utils.mappings.TestMap;
import com.futurealgos.micro.testing.utils.specs.dto.ListResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.text.SimpleDateFormat;


@Getter
@Setter
@SuperBuilder
public class TestsMinimal extends ListResponse implements Serializable {

    public static final String[] FIELDS = {
            TestMap.TEST_ID,
            TestMap.TEST_NAME,
            TestMap.CATEGORY,
            TestMap.CREDITS,
            TestMap.DESCRIPTION,
            TestMap.STATUS,
            TestMap.TIMES_REFERRED,
            TestMap.LANGUAGE,
            BaseMap.CREATED_ON
    };

    private String testId;
    private String testName;
    private Double credits;
    private String testCategory;
    private String description;
    private String status;
    private Boolean discounted;
    private Long noOfTimeReferred;
    private String languageCode;
    private String createdOn;

    public static TestsMinimal build(Test test) {
        return TestsMinimal.builder()
                .identifier(test.getId().toHexString())
                .testId(test.getTestId())
                .discounted(test.getCredits() != null && test.getCredits().hasDiscount())
                .credits(test.getCredits() != null ? test.getCredits().getCalculatedValue() : 0.0)
                .status(test.getStatus().getName())
                .noOfTimeReferred(test.getNoOfTimeReferred())
                .testName(test.getTestName())
                .testCategory(test.getTestCategory().getName())
                .description(test.getDescription())
                .languageCode(test.getLanguage().getCode())
                .createdOn(new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss")
                        .format(test.getCreatedOn()))
                .build();
    }

}
