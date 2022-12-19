/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils.enums;

public enum DefaultRoles {

    UI_ACCESS("UI_ACCESS"),
    IDP_ACCESS("IDP_ACCESS"),
    API_ACCESS("API_ACCESS"),
    ;

    private String tag;

    DefaultRoles(String tag) {
        this.tag = tag;
    }

    public String tag() {
        return tag;
    }
}
