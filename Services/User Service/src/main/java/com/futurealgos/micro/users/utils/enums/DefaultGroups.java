/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils.enums;

/**
 * Package: com.futurealgos.micro.users.utils.enums
 * Project: Prasad Psycho
 * Created On: 14/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public enum DefaultGroups {
    PARTNERS("Partners"),
    ADMINISTRATORS("Super Administrators"),
    IDP_ADMINISTRATORS("IDP Administrators");

    private String tag;

    DefaultGroups(String tag) {
        this.tag = tag;
    }

    public String tag() {
        return tag;
    }
}
