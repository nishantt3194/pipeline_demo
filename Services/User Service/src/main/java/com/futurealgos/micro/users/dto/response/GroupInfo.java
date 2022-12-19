/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;


import com.futurealgos.micro.users.models.base.Group;
import com.futurealgos.micro.users.models.base.Role;

import java.util.List;

public class GroupInfo {

    public String group;
    public String description;
    public List<Role> role;

    public GroupInfo(String group, String description, List<Role> role) {
        this.group = group;
        this.description = description;
        this.role = role;
    }

    public static GroupInfo fromGroup(Group group) {
        return new GroupInfo(
                group.getGroupName(),
                group.getDescription(),
                group.getRoles()
        );
    }
}
