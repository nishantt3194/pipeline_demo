/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TestStatus {

    STAGED("STAGED", "Has been Staged"),
    ACTIVE("ACTIVE", "Status is active"),
    INACTIVE("INACTIVE", "Status is inactive"),
    CREATED("CREATED", "Status is Created");

    private final String name;
    private final String description;

    TestStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TestStatus fromName(String name) {
        TestStatus[] values = values();
        for (TestStatus val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to TestStatus");
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
