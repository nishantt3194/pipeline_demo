/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;

import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.Partner;
import com.futurealgos.micro.testing.repos.PartnerRepository;
import com.futurealgos.micro.testing.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Package: com.futurealgos.micro.testing.services
 * Project: Prasad Psycho
 * Created On: 02/09/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class PartnerService implements FetcherSpec<Partner> {

    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public Partner fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Partner fetch(ObjectId identifier) {
        return partnerRepository.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Partner not found"));
    }

    @Override
    public List<Partner> fetchAll(List<String> identifier) {
        return null;
    }
}
