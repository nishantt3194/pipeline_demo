/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;


import com.futurealgos.micro.users.models.root.BaseEntity;
import com.futurealgos.micro.users.utils.enums.Category;
import com.futurealgos.micro.users.utils.enums.Mode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Document(collection = "credits")
public abstract class Transactions extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1463617010316994206L;

    @Field (name="category")
    private Category category;

    @Field(name = "transaction_value")
    private Double credits;

    @Field
    private Mode mode;

    @Field
    @DocumentReference(collection = "users")
    private String associatedId;


}



