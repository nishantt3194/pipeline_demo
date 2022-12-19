/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.embedded.DocRequestChat;
import com.futurealgos.micro.users.utils.enums.Status;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "document_requests")
public class DocRequest implements Serializable {

    @Id
    ObjectId id;

    @Field
    public String type;

    @Field
    public String name;

    @Field("file_name")
    public String fileName;

    @Field
    @Indexed(unique = true)
    public String tag;

    @Indexed
    @DocumentReference(collection = "document_metadata")
    public DocumentMetadata metadata;

    @Field
    public Status status;

    @Field("submitted_on")
    public Instant submittedOn;

    @Field
    @Builder.Default
    public Set<DocRequestChat> chat = new HashSet<>();

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .append("name", name)
                .append("fileName", fileName)
                .append("tag", tag)
                .append("metadata", metadata)
                .append("status", status)
                .append("chat", chat)
                .toString();
    }
}

