/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.utils.enums;

public enum DocCategory {

    TEST_DOCUMENT("test_document", "tests", "Test Related Documents"),
    PARTNER_DOCUMENT("partner_document", "partners", "Partner Related Documents"),
    LIBRARY_DOCUMENT("library_document", "lib", "Library Related Documents");

    private final String name;
    private final String tag;
    private final String description;

    DocCategory(String name, String tag, String description) {
        this.name = name;
        this.tag = tag;
        this.description = description;
    }

    public static DocCategory fromTag(String tag) {
        DocCategory[] values = values();

        for (DocCategory value : values) {
            if (value.tag.equals(tag)) return value;
        }

        throw new IllegalStateException("Cannot convert " + tag + " DocCategory");
    }

    public static DocCategory fromName(String name) {
        DocCategory[] values = values();

        for (DocCategory value : values) {
            if (value.name.equals(name)) return value;
        }

        throw new IllegalStateException("Cannot convert " + name + " DocCategory");
    }

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return this.description;
    }

}
