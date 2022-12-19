/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.base.Subscale;
import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.utils.enums.EvalWrapperType;

public class STEvalWrapper {
    public String name;
    public Classification classification;
    public Subscale subscale;
    public Norm norm;
    public EvalWrapperType type;

    public STEvalWrapper(String name, Subscale subscale, EvalWrapperType type) {
        this.name = name;
        this.subscale = subscale;
        this.type = type;
    }

    public STEvalWrapper(Classification classification, Norm norm, EvalWrapperType type) {
        this.classification = classification;
        this.name = classification.getValue();
        this.norm = norm;
        this.type = type;
    }
}
