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
public final class RangeClassification extends Classification {
    public Integer min;
    public Integer max;

    @Override
    public String getValue() {
        return min + "-" + max;
    }

    @Override
    public void populate(Document payload) {
        this.min = payload.getInteger("min");
        this.max = payload.getInteger("max");
    }

    @Override
    public void populate(NewClassification payload) {
        Assert.notNull(payload.min(), "Minimum Value is required");
        Assert.notNull(payload.max(), "Maximum Value is required");
        this.min = payload.min();
        this.max = payload.max();
    }

    public RangeClassification() {
    }
}
