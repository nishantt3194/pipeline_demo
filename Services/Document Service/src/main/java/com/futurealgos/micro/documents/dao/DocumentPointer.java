/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.dao;

import com.futurealgos.micro.documents.utils.enums.DocCategory;
import com.futurealgos.micro.documents.utils.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "document_pointer")
public class DocumentPointer extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 9122757786068611558L;

    @Field
    private Status status;

    @Field
    private String path;

    @Field("file_name")
    private String fileName;

    @Field
    private String extension;

    @Field
    private DocCategory category;


    public String buildPath(String extension) {
        if (this.path != null && !this.path.isEmpty()) return this.path;
        this.path = category.getTag() + "/" + id.toHexString() + "." + extension;
        return this.path;
    }

}
