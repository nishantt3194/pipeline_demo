package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.response.users.SecUserInfo;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.repos.primary.UserRepository;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.utils.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/sec/")
public class SecResource {

    @Autowired
    UserService userService;

    @GetMapping(value = "info")
    public SecUserInfo userInfo(@AuthenticationPrincipal User user) {
        if (user != null) {
            return userService.secUserInfo(user);
        } else {
            return null;
        }
    }

    @GetMapping(value = "roles")
    public void roleList() {
        List<Role> roles = Arrays.stream(Role.values()).toList();
    }

}
