/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.Partner;
import com.futurealgos.micro.assessments.repos.PartnerRepo;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 25/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class PartnerService implements FetcherSpec<Partner> {

    @Autowired
    PartnerRepo partnerRepo;

    @Override
    public Partner fetch(String identifier) {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Partner fetch(ObjectId identifier) {
        return partnerRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find Partner with ID " + identifier.toHexString()));
    }

    @Override
    public List<Partner> fetchAll(List<String> identifier) {
        List<Partner> result = new ArrayList<>();
        partnerRepo.findAllById(identifier.stream().map(ObjectId::new)
                .toList()).forEach(result::add);

        return result;
    }
}
