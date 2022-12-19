/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LikertScoring {
    STRAIGHT("Straight", "Straight"),
    REVERSE("Reverse", "Reverse");

    private final String name;
    private final String description;

    LikertScoring(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static LikertScoring fromName(String name) {
        LikertScoring[] values = values();
        for (LikertScoring val :
                values) {
            if (val.getName().equalsIgnoreCase(name)) return val;
        }

        throw new IllegalStateException("Could convert " + name + " to LikertScoringEnum");
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isReverse() {
        return this.equals(REVERSE);
    }
}
