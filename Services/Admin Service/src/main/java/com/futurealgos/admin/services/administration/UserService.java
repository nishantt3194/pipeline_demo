package com.futurealgos.admin.services.administration;


import com.futurealgos.admin.dto.payload.users.AssignMachine;
import com.futurealgos.admin.dto.payload.users.NewUser;
import com.futurealgos.admin.dto.payload.users.UpdateUser;
import com.futurealgos.admin.dto.response.machine.MachineMinimal;
import com.futurealgos.admin.dto.response.machine.MachineSearch;
import com.futurealgos.admin.dto.response.users.SecUserInfo;
import com.futurealgos.admin.dto.response.users.UserInfo;
import com.futurealgos.admin.dto.response.users.UserMinimal;
import com.futurealgos.admin.dto.response.users.UserSearch;
import com.futurealgos.admin.exception.exceptions.NotFoundException;
import com.futurealgos.admin.models.primary.Machine;
import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.models.views.UserAreaAssociation;
import com.futurealgos.admin.repos.primary.UserRepository;
import com.futurealgos.admin.repos.views.UserAreaAssociationRepository;
import com.futurealgos.admin.services.machines.MachineService;
import com.futurealgos.admin.utils.enums.AssignEnum;
import com.futurealgos.admin.utils.enums.Role;
import com.futurealgos.data.service.ServiceTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService extends ServiceTemplate<User, UUID, UserInfo, UserMinimal, UserSearch, NewUser, UpdateUser> {



    UserRepository userRepository;

    UserAreaAssociationRepository userAreaAssociationRepository;

    @Autowired
    MachineService machineService ;

    public UserService(UserRepository userRepository, UserAreaAssociationRepository userAreaAssociationRepository) {
        super(User.class, userRepository, userRepository);
        Assert.notNull(userRepository, "UserRepository must not be null!");
        Assert.notNull(userAreaAssociationRepository, "UserAreaAssociationRepository must not be null!");
        this.userRepository = userRepository;
        this.userAreaAssociationRepository = userAreaAssociationRepository;
        this.converter = NewUser::convert;
    }


    public List<UserAreaAssociation> associations(User user) {
        return userAreaAssociationRepository.findAllByUser(user);
    }

    @Transactional
    public void assignMachine(AssignMachine payload){
        List<Machine> machines = new ArrayList<>();;
        if(!payload.machineIds().isEmpty())
            machines = machineService.fetchAll(payload.machineIds()) ;

        List<User> users = fetchAll(payload.userIds());
        if (payload.assign().equals(AssignEnum.ASSIGN.name())) {
            for (User user : users) {
                user.getMachines().addAll(machines);
                user.setAssigned(true);
                userRepository.save(user);
            }
        }

        if (payload.assign().equals(AssignEnum.UNASSIGN.name())){
            for (User user : users) {
                machines.forEach(user.getMachines()::remove);
                user.setAssigned(true);
                userRepository.save(user);
            }
        }

    }


    public User getUser(String email) throws NotFoundException {
        return userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new NotFoundException("Could not find User: " + email));
    }

    public SecUserInfo secUserInfo (User user ){
        return SecUserInfo.builder()
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getEmail())
                .gid(user.getGid())
                .build();
    }

    @Override
    public UserInfo update(String id, UpdateUser payload, String admin) {
        User user = fetch(id);
        user.setRole(Role.valueOf(payload.role()));
        user.setStatus(payload.status());

        return UserInfo.convert(userRepository.save(user,admin));
    }

    public Page<MachineMinimal> userMachines(String userId){
        User user = fetch(userId);
        return new PageImpl<>(user.getMachines().stream().map(Machine::minimal).toList());
    }
    public List<MachineSearch> userMachineSearch(String userId){
        User user = fetch( userId);
        return  user.getMachines().stream().map(Machine::search).toList();
    }

}
