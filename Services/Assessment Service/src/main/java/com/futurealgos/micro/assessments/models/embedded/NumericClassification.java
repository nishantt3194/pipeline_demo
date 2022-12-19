/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;

import org.bson.Document;

/**
 * Package: com.futurealgos.micro.testing.models.embedded
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public final class NumericClassification extends Classification {
    public Integer value;

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public void populate(Document document) {
        value = document.getInteger("value");
    }

    public NumericClassification() {
    }
}
