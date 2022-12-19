/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Package: com.futurealgos.micro.assessments.models.base
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
@Setter
public class SectionData implements Comparable<SectionData>, Serializable {

    public enum Status {
        ONGOING,
        COMPLETED,
    }

    public String subscale;

    @Field("start_time")
    public Date startTime;

    @Field("end_time")
    public Date endTime;

    @Field("time_limit")
    public Long timeLimit;

    public Status status;

    @Override
    public int compareTo(SectionData sd) {
        return sd.startTime.compareTo(this.startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SectionData that = (SectionData) o;

        return new EqualsBuilder().append(subscale, that.subscale).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(subscale).toHashCode();
    }
}
