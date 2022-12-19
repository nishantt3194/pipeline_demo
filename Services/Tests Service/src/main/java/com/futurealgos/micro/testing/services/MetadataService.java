/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;

import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.DocumentMetadata;
import com.futurealgos.micro.testing.repos.MetadataRepo;
import com.futurealgos.micro.testing.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.testing.services
 * Project: Prasad Psycho
 * Created On: 20/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class MetadataService implements FetcherSpec<DocumentMetadata> {

    @Autowired
    MetadataRepo metadataRepo;

    @Override
    public DocumentMetadata fetch(String identifier) {
        return metadataRepo.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find metadata with id " + identifier));
    }

    @Override
    public DocumentMetadata fetch(ObjectId identifier) {
        return metadataRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find metadata with id " + identifier));
    }

    @Override
    public List<DocumentMetadata> fetchAll(List<String> identifier) {
        List<DocumentMetadata> result = new ArrayList<>();
        metadataRepo.findAllById(identifier.stream()
                .map(ObjectId::new).toList()).forEach(result::add);
        return result;
    }
}
