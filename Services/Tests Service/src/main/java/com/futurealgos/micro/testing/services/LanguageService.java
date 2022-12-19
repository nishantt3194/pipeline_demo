/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;

import com.futurealgos.micro.testing.dto.payload.language.NewLanguage;
import com.futurealgos.micro.testing.dto.response.language.LanguageInfo;
import com.futurealgos.micro.testing.dto.response.language.LanguageMinimal;
import com.futurealgos.micro.testing.dto.response.language.LanguageSearch;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.LanguageStore;
import com.futurealgos.micro.testing.repos.LanguageRepository;
import com.futurealgos.micro.testing.utils.specs.services.ServiceSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService implements ServiceSpec<LanguageStore, NewLanguage, LanguageInfo, LanguageMinimal> {

    @Autowired
    LanguageRepository languageRepository;

    /**
     *To fetch LanguageStore from id
     *
     * @param identifier  String Value of LaguageStore ObjectId
     * @return Object Of Language Store
     */
    @Override
    public LanguageStore fetch(String identifier) {
        return languageRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Language for " + identifier));
    }

    /**
     *
     * @param identifier ObjectId Of Language Store
     * @return Object of LanguageStore
     */
    @Override
    public LanguageStore fetch(ObjectId identifier) {
        return languageRepository.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find Language for " + identifier.toHexString()));
    }

    /**
     *
     * @param identifier List of String values of LanguageStore ObjectId
     * @return List of LanguageStore Object
     */
    @Override
    public List<LanguageStore> fetchAll(List<String> identifier) {
        List<LanguageStore> result = new ArrayList<>();
        languageRepository.findAllById(identifier.stream().map(ObjectId::new).toList())
                .forEach(result::add);

        return result;
    }

    /**
     * To create a New Language Store
     *
     * @param payload NewLanguage payload as an Argument
     * @param admin
     */
    @Override
    public void create(NewLanguage payload, String admin) {
        languageRepository.save(payload.convert(), admin);
    }

    /**
     * To get the Info of LanguageStore
     *
     * @param identifier String value of LanguageStore ObjectId
     * @return LanguageInfo of respective Id.
     */
    @Override
    public LanguageInfo info(String identifier) {
        return null;
    }

    /**
     * @param entity Object of languageStore
     * @param pageable Request containing page size and offset of the page
     * @return Page of LanguageMinimal
     */
    @Override
    public Page<LanguageMinimal> list(LanguageStore entity, Pageable pageable) {
        return null;
    }

    /**
     *
     * @param pageable Request containing page size and offset of the page
     * @return Page of LanguageMinimal
     */
    @Override
    public Page<LanguageMinimal> list(Pageable pageable) {
        return languageRepository.findAll(pageable).map(LanguageMinimal::build);
    }

    /**
     *
     * @return the total no. of languageStore in Repository
     */
    @Override
    public Long count() {
        return languageRepository.count();
    }

    public List<LanguageSearch> search() {
        return languageRepository.findAll()
                .stream()
                .map(LanguageSearch::new).toList();
    }
}
