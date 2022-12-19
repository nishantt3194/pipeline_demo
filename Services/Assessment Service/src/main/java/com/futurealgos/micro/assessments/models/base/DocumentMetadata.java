/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;



import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Package: com.futurealgos.micro.testing.models.base
 * Project: Prasad Psycho
 * Created On: 20/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Document(collection = "document_metadata")
public class DocumentMetadata extends BaseEntity {

    @Field
    private Map<String, String> attributes;

    @Indexed(unique = true)
    private String tag;

    public DocumentMetadata() {
    }

}
