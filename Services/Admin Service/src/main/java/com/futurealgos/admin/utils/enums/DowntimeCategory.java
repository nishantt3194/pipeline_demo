package com.futurealgos.admin.utils.enums;

public enum DowntimeCategory {

    QUALITY_LOSS("QUALITY_LOSS", ""),
    PERFORMANCE_LOSS("PERFORMANCE_LOSS", ""),

    AVAILABILITY_LOSS("AVAILABILITY_LOSS", ""),

    UTILISATION_LOSS("UTILISATION_LOSS", "");

    private final String name;
    private final String description;

    DowntimeCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
