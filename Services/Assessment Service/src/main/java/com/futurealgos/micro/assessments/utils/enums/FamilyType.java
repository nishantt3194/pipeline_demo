/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

public enum FamilyType {
    NUCLEAR("Nuclear"), SINGLE_PARENT("Single Parent"), JOINT_FAMILY("Joint Family");

    private final String tag;

    FamilyType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
