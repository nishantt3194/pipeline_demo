/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services;


import com.futurealgos.micro.testing.dto.payload.search.SearchDTO;
import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.repos.SearchInterfaceRepository;
import com.futurealgos.micro.testing.repos.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService<T extends BaseEntity> {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    SearchInterfaceRepository searchInterfaceRepository;

    public List<T> search(String tableName, SearchDTO payload) {

        List<T> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        try {
            Class tclass = Class.forName(tableName);

//            list=searchRepository.search(tclass,payload.fields(),payload.searchFieldsDTOList());

            for (T t : list) {
                System.out.println(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //throw new ClassNotFoundException("class "+ tableName +" not found");
        }

        return list;
    }

}



