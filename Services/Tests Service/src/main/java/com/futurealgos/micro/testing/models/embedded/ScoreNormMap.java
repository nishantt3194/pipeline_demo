/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.models.base.Norm;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ScoreNormMap {
    public Norm norm;
    public Classification classification;

    public ScoreNormMap(Norm norm, Classification classification) {
        this.norm = norm;
        this.classification = classification;
    }
}
