/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.dto.payload.norms.NewClassification;
import org.bson.Document;
import org.modelmapper.internal.util.Assert;

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
    public void populate(Document payload) {
        this.value = payload.getInteger("value");
    }

    @Override
    public void populate(NewClassification payload) {
        Assert.notNull(payload.value(), "value is required");
        this.value = payload.value();
    }

    public NumericClassification() {
    }
}
