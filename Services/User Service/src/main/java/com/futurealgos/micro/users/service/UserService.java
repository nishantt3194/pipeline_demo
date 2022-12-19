/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;


import com.futurealgos.micro.users.dto.payload.StatusPayload;
import com.futurealgos.micro.users.dto.payload.StatusUpdate;
import com.futurealgos.micro.users.dto.payload.docs.ChatPayload;
import com.futurealgos.micro.users.dto.payload.docs.DocPayload;
import com.futurealgos.micro.users.dto.payload.pipeline.AttachmentsPayload;
import com.futurealgos.micro.users.dto.payload.users.NewAssessor;
import com.futurealgos.micro.users.dto.payload.users.NewPartner;
import com.futurealgos.micro.users.dto.payload.users.NewUser;
import com.futurealgos.micro.users.dto.payload.users.UserRole;
import com.futurealgos.micro.users.dto.response.PartnerSearch;
import com.futurealgos.micro.users.dto.response.RequestInfo;
import com.futurealgos.micro.users.dto.response.UserInfo;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.exceptions.exceptions.UnauthorizedException;
import com.futurealgos.micro.users.models.base.*;
import com.futurealgos.micro.users.models.embedded.DocRequestChat;
import com.futurealgos.micro.users.repo.DocRequestRepo;
import com.futurealgos.micro.users.repo.UserRepo;
import com.futurealgos.micro.users.utils.enums.DefaultDirectory;
import com.futurealgos.micro.users.utils.enums.DefaultGroups;
import com.futurealgos.micro.users.utils.enums.DefaultRoles;
import com.futurealgos.micro.users.utils.enums.Status;
import com.futurealgos.micro.users.utils.specs.services.InternalUpdateSpec;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserService implements InternalUpdateSpec<User> {

    UserRepo userRepo;
    DocRequestRepo docRequestRepo;

    GroupService groupService;
    RoleService roleService;
    EntityStoreService entityStoreService;
    MetadataService metadataService;
    DirectoryService directoryService;
    RegistrationService registrationService;

    ModelMapper mapper;
    PasswordEncoder encoder;

    public UserService(UserRepo userRepo, DocRequestRepo docRequestRepo,
                       GroupService groupService, RoleService roleService,
                       EntityStoreService entityStoreService, MetadataService metadataService,
                       DirectoryService directoryService, RegistrationService registrationService,
                       ModelMapper mapper, PasswordEncoder encoder) {
        Assert.notNull(userRepo, "UserRepo cannot be null");
        Assert.notNull(docRequestRepo, "DocRequestRepo cannot be null");
        Assert.notNull(groupService, "GroupService cannot be null");
        Assert.notNull(roleService, "RoleService cannot be null");
        Assert.notNull(entityStoreService, "EntityStoreService cannot be null");
        Assert.notNull(metadataService, "MetadataService cannot be null");
        Assert.notNull(directoryService, "DirectoryService cannot be null");
        Assert.notNull(registrationService, "RegistrationService cannot be null");
        Assert.notNull(mapper, "ModelMapper cannot be null");
        Assert.notNull(encoder, "PasswordEncoder cannot be null");


        this.userRepo = userRepo;
        this.docRequestRepo = docRequestRepo;
        this.groupService = groupService;
        this.roleService = roleService;
        this.entityStoreService = entityStoreService;
        this.metadataService = metadataService;
        this.directoryService = directoryService;
        this.registrationService = registrationService;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public User createPartnerAdmin(NewPartner payload) {
        User user = userRepo.findByUsername(payload.email).orElse(null);
        if (user != null)
            throw new UnauthorizedException("Partner already exists with this email");
        user = mapper.map(payload, User.class);

        user.setStatus(true);
        user.setFirstTimeLogin(true);
        user.setUsername(payload.email);
        user.setFirstName(payload.adminFirstName);
        user.setLastName(payload.adminLastName);
        user.setPassword(encoder.encode(payload.password));

        Role uiAccess = roleService.fetch(DefaultRoles.UI_ACCESS.tag());

        AuthDirectory directory = directoryService.fetchByName(DefaultDirectory.PARTNERS.tag());
        user.setDirectory(directory);

        Group partner = groupService.getGroup(DefaultGroups.PARTNERS.tag());
        user.setPrimaryGroup(partner);

        user.setGroup(List.of(partner));
        user.setRole(List.of(uiAccess));

        user = userRepo.save(user, "");

        registrationService.remove(payload.preId);
        return user;
    }

    public void createUser(NewUser payload) {

        User user = userRepo.findByUsername(payload.username).orElse(null);
        if (user != null)
            throw new UnauthorizedException("User already exists with this username");
        user = mapper.map(payload, User.class);

        user.setStatus(true);
        user.setUsername(payload.username);
        user.setFirstName(payload.firstname);
        user.setLastName(payload.lastname);
        user.setPassword(encoder.encode(payload.password));

        List<Role> roles = roleService.fetch(payload.role);
        if (roles.size() != payload.role.size()) {
            throw new InputMismatchException(("one of the role is incorrect"));
        }
        user.setRole(roles);

        List<Group> groups = groupService.fetchGroupData(payload.group);

        if (groups.size() != payload.group.size()) {
            throw new InputMismatchException("one of the group is incorrect");
        }

        user.setGroup(groups);
        userRepo.save(user);
    }

    public User getUser(String username) throws NotFoundException {
        return userRepo.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new NotFoundException("Could not find User with email " + username));
    }


    public User fetch(String identifier) throws NotFoundException, InvalidIdentifierFormat {
        if (!ObjectId.isValid(identifier)) throw new InvalidIdentifierFormat("Invalid Identifier Format");
        return userRepo.findById(new ObjectId(identifier)).orElseThrow(() -> new NotFoundException("Could not find User with ID " + identifier));
    }

    public UserInfo approvePartner(String id, String admin) throws InvalidIdentifierFormat {
        User user = fetch(id);
        if (user.getPartner().getStatus().equals(Partner.Status.ACTIVE))
            throw new UnauthorizedException("Partner is already approved");

        user.getPartner().setStatus(Partner.Status.ACTIVE);

        return UserInfo.fromUser(userRepo.save(user, admin), entityStoreService.findAll(), true);

    }

    public UserInfo updateUserStatus(StatusUpdate payload, String admin) throws InvalidIdentifierFormat {
        User user = fetch(payload.id);
        user.setStatus(payload.status);

        user = userRepo.save(user, "");
        return UserInfo.fromUser(user, entityStoreService.findAll(), true);
    }

    public void addUserRole(UserRole payload) {
        User user = getUser(payload.username);

        List<Role> roles = roleService.fetch(payload.role);

        if (roles.isEmpty()) {
            throw new InputMismatchException("could not find role name:" + payload.role);
        }
        if (Collections.disjoint(user.getRole(), payload.role)) {
            throw new UnauthorizedException("one of the role already exists");
        }
        user.getRole().addAll(roles);
        userRepo.save(user);
    }

//    public Page<MinimalPartner> fetchPartners(Pageable pageable, Partner.Status status) {
//        Group partner = groupService.getGroup(DefaultGroups.PARTNERS.tag());
//
//        return userRepo.findUsersByPrimaryGroupAndDetails_PartnerStatus(
//                        partner, status, pageable)
//                .map(MinimalPartner::from);
//    }

    public UserInfo getPartnerInfo(String email) {
        return UserInfo.from(getUser(email));
    }

    public UserInfo getUserInfo(String id, boolean showComplete) throws NotFoundException, InvalidIdentifierFormat {
        return UserInfo.fromUser(fetch(id), entityStoreService.findAll(), showComplete);

    }

//    Document Request Management ======================================================================================

    public AttachmentsPayload attach(AttachmentsPayload payload) throws InvalidIdentifierFormat {
        DocumentMetadata metadata = metadataService.fetch(payload.metadata);
        DocRequest request = docRequestRepo.findByTag(payload.tag).orElseThrow(null);

        if (request != null) {
            request.setStatus(Status.PENDING);
            request.setFileName(payload.fileName);
            request.setTag(payload.getTag());
            request.setMetadata(metadata);
            payload.attached = true;
            docRequestRepo.save(request);
            return payload;
        }
        payload.attached = false;
        return payload;
    }


    /**
     * @param request : payload DocPayload containing,
     */
    public void requestDocument(DocPayload request) throws NotFoundException, InvalidIdentifierFormat {
        User user = fetch(request.id());

        Set<DocRequestChat> chat = new HashSet<>();
        if (request.remark() != null && !request.remark().isBlank()) {
            chat.add(DocRequestChat.builder()
                    .sender(DocRequestChat.Sender.valueOf(request.initiator().toUpperCase()))
                    .message(request.remark())
                    .timestamp(Instant.now()).build());
        }

        DocRequest newRequest = DocRequest.builder()
                .name(request.name())
                .type(request.category())
                .chat(chat)
                .tag(request.tag() != null ? request.tag() : UUID.randomUUID().toString())
                .status(request.tag() != null && !request.tag().isBlank() ? Status.PENDING : Status.REQUESTED)
                .build();

        newRequest = docRequestRepo.save(newRequest);
        user.getPartner().getRequests().add(newRequest);
        user = userRepo.save(user);
    }

    public RequestInfo modifyDocRequestStatus(StatusPayload payload) {
        DocRequest request = docRequestRepo.findById(new ObjectId(payload.getId()))
                .orElseThrow(() -> new NotFoundException("Could not find request with ID " + payload.getId()));

        request.setStatus(Status.valueOf(payload.getStatus()));

        request = docRequestRepo.save(request);
        return RequestInfo.fromDocRequest(request);
    }

    public Set<DocRequestChat> updateChat(ChatPayload payload) {
        DocRequest request = docRequestRepo.findById(new ObjectId(payload.id()))
                .orElseThrow(() -> new NotFoundException("Could not find request with given ID"));

        DocRequestChat chat = DocRequestChat.builder()
                .message(payload.message())
                .sender(DocRequestChat.Sender.valueOf(payload.sender()))
                .timestamp(Instant.now())
                .build();
        request.getChat().add(chat);
        request = docRequestRepo.save(request);

        return request.getChat();
    }


    public List<PartnerSearch> search() {

        return userRepo.findAll()
                .stream()
                .map(PartnerSearch::new).toList();
    }


    @Override
    public User update(User payload, String admin) {
        return userRepo.save(payload, admin);
    }

    public User createAccessor(NewAssessor payload, String admin) {
        User accessor = User.builder()
                .firstName(payload.firstName())
                .lastName(payload.lastName())
                .username(payload.email())
                .status(true)
                .firstTimeLogin(true)
                .build();

        Role uiAccess = roleService.fetch(DefaultRoles.UI_ACCESS.tag());

        AuthDirectory directory = directoryService.fetchByName(DefaultDirectory.PARTNERS.tag());
        accessor.setDirectory(directory);

        Group partner = groupService.getGroup(DefaultGroups.PARTNERS.tag());
        accessor.setPrimaryGroup(partner);

        accessor.setGroup(List.of(partner));
        accessor.setRole(List.of(uiAccess));

        return userRepo.save(accessor, admin);
    }
}
