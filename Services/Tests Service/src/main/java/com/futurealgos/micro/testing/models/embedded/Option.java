/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.models.base.DocumentMetadata;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Option implements Comparable<Option> {

    private int index;
    private String description;
    @DocumentReference(collection = "document_metadata")
    private DocumentMetadata image;
    private Integer score;

    @Override
    public int compareTo(@NotNull Option o) {
        return Integer.compare(index, o.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("index", index)
                .append("description", description)
                .append("image", image)
                .append("score", score)
                .toString();
    }
}
