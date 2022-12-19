/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.norms;

import com.futurealgos.micro.assessments.models.base.Norm;
import com.futurealgos.micro.assessments.models.embedded.Classification;
import com.futurealgos.micro.assessments.models.embedded.RequiredDataHolder;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Builder
public class RequiredDataMap implements Serializable, Comparable<RequiredDataMap> {

    public String id;
    public String norm;
    public String[] options;

    public Norm.Type classificationType;

    public RequiredDataHolder.Type type;

    public String value;


    public static RequiredDataMap build(RequiredDataHolder holder){
        String[] options = holder.getNorm().getClassification().stream().map(Classification::getValue).toArray(String[]::new);
        RequiredDataMap map = RequiredDataMap.builder()
                .id(holder.getId())
                .norm(holder.getNorm().getName())
                .type(holder.getType())
                .classificationType(holder.getNorm().getType())
                .value(holder.getValue())
                .options(options)
                .build();

        return map;
    }


    @Override
    public int compareTo(RequiredDataMap o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RequiredDataMap that = (RequiredDataMap) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
