/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;


import com.futurealgos.micro.users.dto.payload.users.PreRegisterDto;
import com.futurealgos.micro.users.dto.response.MinRegister;
import com.futurealgos.micro.users.dto.response.RegisterInfo;
import com.futurealgos.micro.users.exceptions.exceptions.AlreadyExistsException;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.models.base.PreRegister;
import com.futurealgos.micro.users.repo.PreRegistrationRepo;
import com.futurealgos.micro.users.utils.specs.services.FetcherSpec;
import com.futurealgos.micro.users.utils.specs.services.ListSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService implements ListSpec<PreRegister, MinRegister>, FetcherSpec<PreRegister> {

    @Autowired
    PreRegistrationRepo registerRepo;

    @Autowired
    MailService mailService;

    @Override
    public PreRegister fetch(String identifier) {
        return registerRepo.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not found registration request with id " + identifier));
    }

    @Override
    public PreRegister fetch(ObjectId identifier) {
        return registerRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not found registration request with id " + identifier));
    }

    @Override
    public List<PreRegister> fetchAll(List<String> identifier) {
        List<PreRegister> result = new ArrayList<>();
        registerRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }

    public void init(PreRegisterDto payload) {
        boolean exists = registerRepo.existsByEmail(payload.email);

        if (exists) {
            throw new AlreadyExistsException("Partner already registered with email");
        }
        PreRegister register = PreRegister.builder()
                .name(payload.name)
                .email(payload.email)
                .country(payload.country)
                .phoneNo(payload.phoneNo).build();

        register = registerRepo.save(register, "");
        mailService.sendWelcomeEmail(register.getId().toHexString(), register.getName(), register.getEmail());
    }

    public void remove(String id) {
        registerRepo.deleteById(new ObjectId(id));
    }


    @Override
    public Page<MinRegister> list(PreRegister entity, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public Page<MinRegister> list(Pageable pageable) {
        return registerRepo.findAll(pageable).map(MinRegister::fromPreRegister);
    }

    public RegisterInfo info(String id) {
        PreRegister user = registerRepo.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("No Partner Registration has been initiated with given ID"));


        return RegisterInfo.builder()
                .preId(user.getId().toHexString())
                .name(user.getName())
                .email(user.getEmail())
                .country(user.getCountry())
                .phoneNo(user.getPhoneNo()).build();
    }


}
