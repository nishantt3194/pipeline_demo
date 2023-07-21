package com.futurealgos.admin.dto.response.machine;


import com.futurealgos.admin.models.primary.Category;

public interface OeeParetoQuery {

    Category getCategory();

    long getDowntime();

}
