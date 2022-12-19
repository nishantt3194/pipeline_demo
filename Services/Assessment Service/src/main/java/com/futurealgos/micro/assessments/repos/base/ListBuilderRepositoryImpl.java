/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.repos.base;


import com.futurealgos.micro.assessments.models.root.BaseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public class ListBuilderRepositoryImpl<T extends BaseEntity> implements ListBuilderRepository<T> {

    @Autowired
    private MongoTemplate template;


    @Override
    @Transactional(readOnly = true)
    public Page<T> list(Class<T> entity, Pageable pageable) {
        Query query;


        if (pageable.isUnpaged())
            query = new Query()
                    .allowDiskUse(true);

        else {
            query = new Query().with(pageable)
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .allowDiskUse(true);
        }

        List<T> data = template.find(query, entity);

        if (!pageable.isUnpaged()) {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            () -> template.count(query.skip(-1).limit(-1), entity));
        } else {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            data::size);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> list(Class<T> entity, String[] fields, Pageable pageable) {
        Query query;
        if (pageable.isUnpaged())
            query = new Query()
                    .allowDiskUse(true);

        else {
            query = new Query().with(pageable)
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .allowDiskUse(true);
        }


        query.fields().include(fields);

        List<T> data = template.find(query, entity);

        if (!pageable.isUnpaged()) {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            () -> template.count(query.skip(-1).limit(-1), entity));
        } else {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            data::size);
        }
    }

    @Override
    public Page<T> list(Class<T> entity, String[] fields, Map<String, Object> map, Pageable pageable) {
        Query query;
        if (pageable.isUnpaged())
            query = new Query()
                    .allowDiskUse(true);

        else {
            query = new Query().with(pageable)
                    .skip((long) pageable.getPageSize() * pageable.getPageNumber())
                    .limit(pageable.getPageSize())
                    .allowDiskUse(true);
        }

        query.fields().include(fields);
//        for(Map.Entry<String,String>  entry: map.entrySet()) {
//            query.addCriteria(Criteria.where(entry.getKey()).regex(entry.getValue()));
//        }


        List<T> data = template.find(query, entity);

        if (!pageable.isUnpaged()) {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            () -> template.count(query.skip(-1).limit(-1), entity));
        } else {
            return PageableExecutionUtils
                    .getPage(data, pageable,
                            data::size);
        }
    }
}
