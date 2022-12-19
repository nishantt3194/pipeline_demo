/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Mode {
    DEFAULT("DEFAULT", "Is default"),
    USER_DEFINED("USER_DEFINED", "Is User Defined");
    private final String name;
    private final String description;

    Mode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Mode fromName(String name) {
        Mode[] values = values();
        for (Mode val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to SubscaleType");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
