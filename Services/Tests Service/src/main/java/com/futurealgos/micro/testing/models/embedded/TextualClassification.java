/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.dto.payload.norms.NewClassification;
import org.bson.Document;
import org.springframework.util.Assert;

/**
 * Package: com.futurealgos.micro.testing.models.embedded
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public final class TextualClassification extends Classification {
    public String value;

    @Override
    public void populate(NewClassification payload) {
        Assert.notNull(payload.text(), "Classification is required");
        this.value = payload.text();
    }

    @Override
    public void populate(Document payload) {
        this.value = payload.getString("value");
    }

    @Override
    public String getValue() {
        return value;
    }

    public TextualClassification() {
    }
}
