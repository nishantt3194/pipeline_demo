/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;


import com.futurealgos.micro.assessments.models.embedded.Option;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.LikertScoring;
import com.futurealgos.micro.assessments.utils.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "question")
public class Question extends BaseEntity implements Serializable, Comparable<Question> {

    @Serial
    private static final long serialVersionUID = -6953678920896239616L;
    @Field("question_number")
    private String questionNumber;

    @Field("question_type")
    private QuestionType type;

    @Field("creation_index")
    private Integer index;

    @Field
    private LikertScoring likertScoring;

    @Field("description")
    private String description;

    @Field("question_image")
    @DocumentReference(collection = "document_metadata")
    private DocumentMetadata questionImage;

    @Field
    @DocumentReference(collection = "subscale")
    private Subscale subscale;

    @Field
    private List<Option> options;

    @Override
    public int compareTo(@NotNull Question o) {
        return Integer.compare(this.index, o.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("questionNumber", questionNumber)
                .append("type", type)
                .append("index", index)
                .append("likertScoring", likertScoring)
                .append("description", description)
                .append("questionImage", questionImage)
                .append("subscale", subscale)
                .append("options", options)
                .append("id", id.toHexString())
                .toString();
    }
}
