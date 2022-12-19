/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AdministrationEnum {
    GROUP("GROUP", "Admin of a group"), INDIVIDUAL("INDIVIDUAL", "Admin is an individual");
    private final String name;
    private final String description;

    AdministrationEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AdministrationEnum fromName(String name) {
        AdministrationEnum[] values = values();
        for (AdministrationEnum val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to AdministrationEnum");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
