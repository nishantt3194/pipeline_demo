/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.models.base.AuthDirectory;
import com.futurealgos.micro.users.repo.DirectoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Package: com.futurealgos.micro.users.service
 * Project: Prasad Psycho
 * Created On: 21/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class DirectoryService {

    @Autowired
    DirectoryRepo directoryRepo;


    public AuthDirectory fetchByName(String name) {
        return directoryRepo.findByName(name)
                .orElseThrow(() -> new NotFoundException("Could not find " + name + " directory"));
    }
}
