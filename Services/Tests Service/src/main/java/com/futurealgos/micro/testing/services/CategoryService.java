/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;

import com.futurealgos.micro.testing.dto.payload.category.NewCategory;
import com.futurealgos.micro.testing.dto.response.category.CategoryInfo;
import com.futurealgos.micro.testing.dto.response.category.CategoryMinimal;
import com.futurealgos.micro.testing.dto.response.category.CategorySearch;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.models.base.TestCategory;
import com.futurealgos.micro.testing.repos.CategoryRepository;
import com.futurealgos.micro.testing.utils.specs.services.ServiceSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ServiceSpec<TestCategory, NewCategory, CategoryInfo, CategoryMinimal> {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * To fetch the Test Category from String value of Id
     *
     * @param identifier It takes Test Category's Id as String in params
     * @return Object of TestCategory
     */
    @Override
    public TestCategory fetch(String identifier) {
        return categoryRepository.findById(new ObjectId(identifier)).orElseThrow(() -> new NotFoundException("could not found " +
                "norm " + identifier));
    }

    /**
     * To fetch the Test Category from  ObjectId
     *
     * @param identifier  TestCategory ObjectId in Param
     * @return Object Of TestCategory
     */
    @Override
    public TestCategory fetch(ObjectId identifier) {
        return categoryRepository.findById(identifier).orElseThrow(() -> new NotFoundException("could not found " +
                "norm " + identifier.toHexString()));

    }

    /**
     *
     * @param identifiers  List of String identifiers
     * @return List of TestCategory
     */
    @Override
    public List<TestCategory> fetchAll(List<String> identifier) {
        List<TestCategory> result = new ArrayList<>();
        categoryRepository.findAllById(identifier.stream().map(ObjectId::new)
                .collect(Collectors.toList())).forEach(result::add);

        return result;


    }

    /**
     *
     * @param payload : NewCategory payload
     * @param admin
     */
    @Override
    public void create(NewCategory payload, String admin) {
        categoryRepository.save(payload.convert(), admin);
    }

    /**
     * To Retrive the info of TestCategory
     *
     * @param identifier Takes String value of Id.
     * @return Object of CategoryInfo.
     *
     */
    @Override
    public CategoryInfo info(String identifier) {

        ObjectId id = new ObjectId(identifier);
        Optional<TestCategory> optionalTestCategory = categoryRepository.findById(id);

        if (!optionalTestCategory.isPresent())
            throw new NotFoundException("could not found test category" + id.toHexString());
        TestCategory testCategory = optionalTestCategory.get();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(testCategory.getCreatedOn());
        CategoryInfo categoryInfo = CategoryInfo.builder()
                .name(testCategory.getName())
                .description(testCategory.getDescription())
                .status(testCategory.getStatus().name())
                .createdDate(strDate)
                .build();
        return categoryInfo;
    }

    /**
     * Count of TestCategory
     *
     * @return returns a Long value count of testCategory
      */
    @Override
    public Long count() {
        return categoryRepository.count();
    }

    /**
     *
     * @param entity Take TestCategory entity as Param
     * @param pageable Request containing page size and offset of the page
     * @return Page of CategoryMinimal
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategoryMinimal> list(TestCategory entity, Pageable pageable) {
        Example<TestCategory> criteria = Example.of(entity);
        return categoryRepository.findAll(criteria, pageable).map(CategoryMinimal::build);

    }

    /**
     *
     * @param pageable Request containing page size and offset of the page
     * @return Page of CategoryMinimal
     */
    @Override
    public Page<CategoryMinimal> list(Pageable pageable) {
        return categoryRepository.list(TestCategory.class, pageable).map(CategoryMinimal::build);
    }

    /**
     * Search method which will return Name of categories
     *
     * @return List of Category Search i.e. name and Id of the  categories
     */
    public List<CategorySearch> search() {
        return categoryRepository.findAll()
                .stream()
                .map(CategorySearch::new).toList();
    }


}
