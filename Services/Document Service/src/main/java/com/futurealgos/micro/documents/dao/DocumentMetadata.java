/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.dao;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@Document(collection = "document_metadata")
public class DocumentMetadata extends BaseEntity implements Serializable {

    @Field
    private Map<String, String> attributes;

    @Indexed(unique = true)
    private String tag;

    @DocumentReference(collection = "document_pointer")
    private DocumentPointer pointer;

    private List<String> history;

    @Field("doc_version")
    private Integer documentVersion;


    public DocumentMetadata() {
    }
}
