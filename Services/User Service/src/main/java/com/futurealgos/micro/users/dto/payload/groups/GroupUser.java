/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.groups;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class GroupUser {

    public String username;
    public List<String> group;


}
