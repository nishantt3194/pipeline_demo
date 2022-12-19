/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueuedObject implements Comparable<QueuedObject> {
    private String tag;
    private String id;
    private String alias;
    private String status;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tag", tag)
                .append("id", id)
                .append("alias", alias)
                .append("status", status)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QueuedObject that = (QueuedObject) o;

        return new EqualsBuilder().append(tag, that.tag).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tag).toHashCode();
    }

    @Override
    public int compareTo(QueuedObject object) {
        return this.tag.compareTo(object.tag);
    }
}
