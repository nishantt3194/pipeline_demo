/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;


import com.futurealgos.micro.testing.dto.payload.norms.NewNorm;
import com.futurealgos.micro.testing.dto.response.norms.NormsInfo;
import com.futurealgos.micro.testing.dto.response.norms.NormsMinimal;
import com.futurealgos.micro.testing.dto.response.norms.NormsSearch;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.repos.NormsRepository;
import com.futurealgos.micro.testing.utils.specs.services.ServiceSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NormsService implements ServiceSpec<Norm, NewNorm, NormsInfo, NormsMinimal> {

    @Autowired
    NormsRepository normsRepository;

    /**
     * To fetch the list of Norms
     *
     * @param identifier List of String value of the Norm's Object id.
     * @return List of Norm
     */
    @Override
    public List<Norm> fetchAll(List<String> identifier) {
        List<Norm> result = new ArrayList<>();
        normsRepository.findAllById(identifier.stream().map(ObjectId::new)
                .toList()).forEach(result::add);

        return result;
    }

    /**
     *
     * @param identifier ObjectId of the Norms
     * @return Object of Norm
     */
    @Override
    public Norm fetch(ObjectId identifier) {
        return normsRepository.findById(identifier)
                .orElseThrow(() ->
                        new NotFoundException("Could Not Found Norm: " + identifier.toHexString()));
    }


    /**
     *
     * @param identifier String Value of the Norms ObejectId
     * @return Object of Norm
     */
    @Override
    public Norm fetch(String identifier) {
        ObjectId id = new ObjectId(identifier);
        return normsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Could Not Found Norm: " + id.toHexString()));
    }


    /**
     *To create new Norms
     * @param payload Take NewNorms class as Payload
     * @param admin Administrator who is creating the Norms
     */

    @Override
    public void create(NewNorm payload, String admin) {
        try {
            normsRepository.save(payload.convert(), admin);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Classification Definitions should have a default Constructor");
        }
    }

    /**
     *
     * @param entity Take Norm entity as payload
     * @param pageable Request containing page size and offset of the page
     * @return Page of NormsMinimal
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormsMinimal> list(Norm entity, Pageable pageable) {
        Example<Norm> criteria = Example.of(entity);
        return normsRepository.findAll(criteria, pageable)
                .map(NormsMinimal::build);
    }


    @Override
    public Page<NormsMinimal> list(Pageable pageable) {
        return normsRepository.findAll(pageable).map(NormsMinimal::build);
    }


    /**
     *
     * @param identifier
     * @return
     */
    @Override
    public NormsInfo info(String identifier) {
        return NormsInfo.populate(fetch(identifier));
    }

    @Override
    public Long count() {
        return normsRepository.count();
    }

    public List<NormsSearch> search() {
        return normsRepository.findAll()
                .stream()
                .map(NormsSearch::new).toList();
    }

}
