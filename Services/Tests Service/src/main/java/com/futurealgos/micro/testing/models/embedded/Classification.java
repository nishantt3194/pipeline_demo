/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.dto.payload.norms.NewClassification;
import org.bson.Document;

/**
 * Package: com.futurealgos.micro.testing.models.embedded
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public sealed abstract class Classification permits TextualClassification, RangeClassification, NumericClassification {

    public abstract void populate(NewClassification payload);

    public abstract void populate(Document payload);

    public abstract String getValue();

    public Classification() {}
}
