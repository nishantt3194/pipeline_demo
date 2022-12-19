/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.root.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "document_metadata")
public class DocumentMetadata extends BaseEntity implements Serializable {

    @Field
    private Map<String, String> attributes;
    @Indexed(unique = true)
    private String tag;

    @DocumentReference(collection = "document_pointer")
    private DocumentPointer pointer;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentMetadata that = (DocumentMetadata) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(attributes, that.attributes).append(tag, that.tag).isEquals();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
