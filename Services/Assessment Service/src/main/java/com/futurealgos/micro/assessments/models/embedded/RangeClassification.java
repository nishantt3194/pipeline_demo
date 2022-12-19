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
public final class RangeClassification extends Classification {
    public Integer min;
    public Integer max;

    @Override
    public String getValue() {
        return min + "-" + max;
    }

    @Override
    public void populate(Document document) {
        min = document.getInteger("min");
        max = document.getInteger("max");
    }

    public RangeClassification() {
    }
}
