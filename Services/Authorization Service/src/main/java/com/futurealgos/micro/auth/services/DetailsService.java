/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;

import com.futurealgos.micro.auth.models.main.AuthDirectory;
import com.futurealgos.micro.auth.models.base.User;
import com.futurealgos.micro.auth.repository.base.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class DetailsService implements UserDetailsService {

    UserRepo userRepo;

    public DetailsService(@Autowired UserRepo userRepo) {
        Assert.notNull(userRepo, "User Repository cannot be null");
        this.userRepo = userRepo;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find User"));
    }

    public boolean doesUserExistInDirectory(String username, AuthDirectory directory) {
        User user = userRepo.findByUsername(username)
                .orElse(null);
        return user != null && user.getDirectory() != null && user.getDirectory().getId().toHexString().equals(directory.getId().toHexString());
    }

    public void disableFirstSetup(String username) {
        User user = loadUserByUsername(username);
        user.setFirstTimeLogin(false);
        userRepo.save(user);
    }
}
