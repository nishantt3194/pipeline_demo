/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.payload;

import com.futurealgos.micro.assessments.models.base.Norm;
import com.futurealgos.micro.assessments.models.embedded.Classification;
/**
 * Package: com.futurealgos.micro.testing.dto.payload
 * Project: Prasad Psycho
 * Created On: 20/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public class NormCartesianWrapper {

    public String norm;

    public String classification;

    public NormCartesianWrapper(String norm, String classification) {
        this.norm = norm;
        this.classification = classification;
    }

    public NormCartesianWrapper() {
    }
}
