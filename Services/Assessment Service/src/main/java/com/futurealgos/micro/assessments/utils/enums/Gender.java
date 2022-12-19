/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

public enum Gender {
    MALE("Male"), FEMALE("Female"), OTHERS("Other");

    private final String tag;

    public String getTag() {
        return tag;
    }

    Gender(String tag) {
        this.tag = tag;
    }
}
