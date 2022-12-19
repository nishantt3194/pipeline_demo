/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;


import com.futurealgos.micro.users.models.base.EntityStore;
import com.futurealgos.micro.users.models.base.Group;
import com.futurealgos.micro.users.models.base.Role;
import com.futurealgos.micro.users.models.base.User;
import com.futurealgos.micro.users.utils.enums.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@Builder
public class UserInfo implements Serializable {

    public String id;
    public String username;
    public String firstName;
    public String lastName;
    public String status;

    public List<RoleMatrix> role;
    public List<MinimalGroup> groups;

    public static UserInfo from(User user) {
        String status = user.getPartner() == null ? (user.isStatus() ? "ACTIVE" : "INACTIVE") : user.getPartner().getStatus().toString();
        UserInfoBuilder builder = UserInfo.builder()
                .id(user.getId().toHexString())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(status);

        return builder.build();
    }


    public static UserInfo fromUser(User user, List<EntityStore> store, boolean returnComplete) {
        String status = user.getPartner() == null ? (user.isStatus() ? "ACTIVE" : "INACTIVE") : user.getPartner().getStatus().toString();
        UserInfoBuilder builder = UserInfo.builder()
                .id(user.getId().toHexString())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(status);

        if(returnComplete) {
            List<Group> groups = user.getGroup();
            if(!groups.contains(user.getPrimaryGroup()))
                groups.add(user.getPrimaryGroup());
            List<Role> roles = user.getRole();
            groups.stream().flatMap(g -> g.getRoles().stream()).distinct().forEach(roles::add);
            builder.groups(groups.stream().map(MinimalGroup::dtoFromGroup).toList()).role(buildRoleMatrix(roles, store));
        }

        return builder.build();
    }

    private static List<RoleMatrix> buildRoleMatrix(List<Role> roles, List<EntityStore> store) {
        Map<EntityStore, List<Role>> rawMatrix = roles.stream().collect(groupingBy(Role::getStore));

        List<RoleMatrix> matrices = new ArrayList<>();

        store.forEach(entity -> {
            if(!rawMatrix.containsKey(entity)) {
                rawMatrix.put(entity, new ArrayList<>());
            }
        });

        for (EntityStore key : rawMatrix.keySet()) {
            matrices.add(RoleMatrix.builder()
                            .entity(key.getEntity())
                            .permissions(rawMatrix.get(key).stream().map(Role::getPermission).map(Permission::toString).toList())
                    .build());

        }

        return matrices;
    }
}
