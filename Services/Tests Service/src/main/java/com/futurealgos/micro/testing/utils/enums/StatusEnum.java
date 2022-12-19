/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusEnum {

    ACTIVE("ACTIVE", "Status is active"),
    INACTIVE("INACTIVE", "Status is inactive");

    private final String name;
    private final String description;

    StatusEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatusEnum fromName(String name) {
        StatusEnum[] values = values();
        for (StatusEnum val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + "to StatusEnum");
    }


    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }


}
