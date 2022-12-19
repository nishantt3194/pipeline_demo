/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.exceptions.exceptions.NotFoundException;
import com.futurealgos.micro.users.models.base.DocumentMetadata;
import com.futurealgos.micro.users.repo.MetadataRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Package: com.futurealgos.micro.users.service
 * Project: Prasad Psycho
 * Created On: 19/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@Service
public class MetadataService {

    @Autowired
    MetadataRepo metadataRepo;

    public DocumentMetadata fetch(String identifier) throws InvalidIdentifierFormat, NotFoundException {
        if(!ObjectId.isValid(identifier)) throw new InvalidIdentifierFormat("Invalid Identifier Format");
        return metadataRepo.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Metadata with ID " + identifier));
    }
}
