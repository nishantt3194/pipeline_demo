/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimalGroup {

    public String groupName;
    public String description;

    public MinimalGroup(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }

    public MinimalGroup() {

    }

    public static MinimalGroup dtoFromGroup(Group group) {
        return new MinimalGroup(
                group.getGroupName(),
                group.getDescription()
        );

    }

}
