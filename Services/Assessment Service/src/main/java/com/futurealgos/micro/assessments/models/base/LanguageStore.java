/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "language_store")
public class LanguageStore extends BaseEntity implements Serializable {

    @Field("language_code")
    private String code;

    @Field
    private String tag;

    @Field
    private StatusEnum status;

    @Field
    private String description;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("tag", tag)
                .append("status", status)
                .append("description", description)
                .append("id", id.toHexString())
                .toString();
    }
}
