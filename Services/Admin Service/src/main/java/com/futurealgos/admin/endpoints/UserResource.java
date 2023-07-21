package com.futurealgos.admin.endpoints;

import com.futurealgos.admin.dto.payload.users.AssignMachine;
import com.futurealgos.admin.dto.payload.users.NewUser;
import com.futurealgos.admin.dto.payload.users.UpdateUser;
import com.futurealgos.admin.dto.response.machine.MachineMinimal;
import com.futurealgos.admin.dto.response.users.UserInfo;
import com.futurealgos.admin.dto.response.users.UserMinimal;
import com.futurealgos.admin.dto.response.users.UserSearch;
import com.futurealgos.admin.exception.exceptions.AlreadyExistsException;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.repos.primary.UserRepository;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.utils.enums.AssignEnum;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.admin.utils.mappings.UserMap;
import com.futurealgos.specs.utils.SearchFilter;
import com.futurealgos.specs.utils.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserResource {

    @Autowired
    UserService userService;
    @Autowired
    MachineService machineService;

    @Autowired
    UserRepository userRepo;

    @PostMapping("create")
    public UserInfo create(@RequestBody NewUser newUser) {
        boolean exists = userRepo.existsByEmail(newUser.email());
        if (exists) {
            throw new AlreadyExistsException("User Already exists ");
        } else
            return userService.create(newUser, "");
    }


    @GetMapping("search")
    public List<UserSearch> search(@RequestParam(value = "machine", required = false) String machineId,
                                   @RequestParam(value = "role", required = false) String role) {
        List<SearchFilter<?>> filters = new ArrayList<>();
        if (machineId != null) {
            Machine machine = machineService.fetch(machineId);
            return machine.getOperators().stream().map(User::search).toList();
        }
        if (role != null) {
            filters.add(new SearchFilter<>(UserMap.ROLE, Role.valueOf(role), SearchOperator.IS));
        }

        return userService.search(filters.toArray(SearchFilter[]::new));
    }

    @GetMapping("list")
    public Page<UserMinimal> list(@RequestParam(defaultValue = "-1") int offset,
                                  @RequestParam(defaultValue = "-1") int size,
                                  @RequestParam(required = false) String role) {

        return userService.list(offset, size, new String[0], SearchFilter.is("role", Role.valueOf(role)));
    }

    @GetMapping("info")
    public UserInfo info(@RequestParam String id) {
        return userService.info(id);
    }

    @PostMapping("update")
    public UserInfo update(@RequestBody UpdateUser payload) {
        return userService.update(payload.id(), payload, "");
    }

    @PostMapping("machine")
    public String assignMachine(@RequestBody AssignMachine payload) {
        userService.assignMachine(payload);
        if (payload.assign().equals(AssignEnum.ASSIGN.name())) {
            return "Machine Assigned Successfully ";
        } else if (payload.assign().equals(AssignEnum.UNASSIGN.name())) {
            return "Machine Unassigned Successfully";
        } else {
            return "Failed To get Assign type";
        }

    }

    @GetMapping("list/machine")
    public Page<MachineMinimal> userMachines(@RequestParam String userId) {
        return userService.userMachines(userId);
    }

}
