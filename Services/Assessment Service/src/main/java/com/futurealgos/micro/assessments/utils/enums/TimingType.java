/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TimingType {

    TIMED("TIMED", "Has a certain time limit "), UNTIMED("UNTIMED", "Has no time limit");
    private final String name;
    private final String description;

    TimingType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TimingType fromName(String name) {
        TimingType[] values = values();
        for (TimingType val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to TimingType");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
