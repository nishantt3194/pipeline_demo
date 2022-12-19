/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.futurealgos.micro.testing.models.root.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.users.models.base
 * Project: Prasad Psycho
 * Created On: 20/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Document(collection = "document_pointer")
public class DocumentPointer extends BaseEntity implements Serializable {

    @Field
    private String extension;

}