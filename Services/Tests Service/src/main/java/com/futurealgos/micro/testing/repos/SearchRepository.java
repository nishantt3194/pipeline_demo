/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.repos;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SearchRepository<T extends BaseEntity> {

    @Autowired
    public MongoTemplate template;


//       public List<T> search(Class<T> tClass, String[] fields, List<SearchFieldsDTO> list){
//              Query query= new Query() .allowDiskUse(true);
//              query.fields().include(fields);
//
//              for(SearchFieldsDTO searchFieldsDTO : list  ) {
//
//                     String findBy=searchFieldsDTO.findBy();
//                     String operation =searchFieldsDTO.operation();
//
//                     Object value= searchFieldsDTO.value();
//
//                     if(operation.equals("equal"))
//                     query.addCriteria(Criteria.where(findBy).is(value));
//
//                     else if(operation.equals("isAfter"))
//                     query.addCriteria(Criteria.where(findBy).gt(value));
//
//                     else if(operation.equals("isAfterOrEqual"))
//                     query.addCriteria(Criteria.where(findBy).gte(value));
//
//                     else if(operation.equals("isBefore"))
//                            query.addCriteria(Criteria.where(findBy).lt(value));
//
//                     else if(operation.equals("isBeforeOrEqual"))
//                            query.addCriteria(Criteria.where(findBy).lte(value));
//
//
//
//              }
//
//           query.addCriteria(Criteria.where(k).gt());
//              return template.find(query,tClass);
//
//       }


}







