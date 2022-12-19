/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.groups;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class NewGroup {

    @NotNull
    @NotBlank
    @Pattern(regexp = "[A-Z]+(_[A-Z]+)*")
    public String groupName;

    public String description;

    public List<String> role;

    public List<String> user;


    public NewGroup(String groupName, String description, List<String> role, List<String> user) {
        this.groupName = groupName;
        this.description = description;
        this.role = role;
        this.user = user;
    }


}
