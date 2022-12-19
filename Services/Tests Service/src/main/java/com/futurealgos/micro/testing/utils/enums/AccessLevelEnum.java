/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AccessLevelEnum {
    A("A", "Low Level Access"),
    B("B", "Intermediate Access"),
    C("C", "High Level Access "),
    S("S", "Super user Access");

    private final String name;
    private final String description;

    AccessLevelEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AccessLevelEnum fromName(String name) {
        AccessLevelEnum[] values = values();
        for (AccessLevelEnum val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to AccessLevelEnum");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
