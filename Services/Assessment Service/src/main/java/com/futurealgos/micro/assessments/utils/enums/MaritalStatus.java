/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

public enum MaritalStatus {
    BACHELOR("Bachelor"), MARRIED("Married"), DIVORCED("Divorced"), WIDOWED("Widowed");

    private final String tag;

    MaritalStatus(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }
}
