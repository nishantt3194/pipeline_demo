/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;


import com.futurealgos.micro.assessments.models.base.Norm;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@NoArgsConstructor
public class ScoreNormMap {
    public Norm norm;
    public Classification classification;

    public ScoreNormMap(Norm norm, Classification classification) {
        this.norm = norm;
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScoreNormMap that = (ScoreNormMap) o;

        return new EqualsBuilder().append(norm.getId().toHexString(),
                that.norm.getId().toHexString()).append(classification.getValue(),
                that.classification.getValue()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(norm.getId().toHexString()).append(classification.getValue()).toHashCode();
    }
}
