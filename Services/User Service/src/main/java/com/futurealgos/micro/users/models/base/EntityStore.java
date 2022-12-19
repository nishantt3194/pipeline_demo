/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document(collection = "entity_store")
@Getter
@Setter
public class EntityStore implements Serializable {

    @Serial
    private static final long serialVersionUID = 4230719635753279531L;

    @Id
    public ObjectId id;

    private String entity;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createdOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updatedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityStore that = (EntityStore) o;
        return Objects.equals(id, that.id) && Objects.equals(entity, that.entity) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entity, description);
    }

}