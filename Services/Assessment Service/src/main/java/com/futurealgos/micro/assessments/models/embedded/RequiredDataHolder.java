/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;

import com.futurealgos.micro.assessments.models.base.Norm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.UUID;

/**
 * Package: com.futurealgos.micro.assessments.models.embedded
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
public class RequiredDataHolder {

    public enum Type {
        NUMBER, STRING;
    }

    private String id;

    @DocumentReference(collection = "norms")
    private Norm norm;

    private Type type;

    private String value;

    public RequiredDataHolder(Norm norm) {
        this.id = UUID.randomUUID().toString();
        this.norm = norm;
    }

    public RequiredDataHolder(Norm norm, String value) {
        this.id = UUID.randomUUID().toString();
        this.norm = norm;
        this.value = value;
    }

    public RequiredDataHolder(String id, Norm norm, Type type, String value) {
        this.id = id;
        this.norm = norm;
        this.type = type;
        this.value = value;
    }

    public RequiredDataHolder() {

    }
}
