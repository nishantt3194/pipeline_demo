/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.futurealgos.micro.assessments.models.root.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashMap;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "score")
public class Score extends BaseEntity implements Serializable {

    @Field("max_score")
    private Double maxRawScore;
    @Field("min_score")
    private Double minRawScore;
    @Field("qualitative_descriptor")
    @DocumentReference(collection = "qualitative_descriptor")
    private QualitativeDescriptor qualitativeDescriptor;

    @Field("detailed_interpretation")
    @DocumentReference(collection = "detailed_interpretation")
    private DetailedInterpretation detailedInterpretation;

    @Field("additional_scores")
    private HashMap<String, String> additionalScore;

    @JsonBackReference
    public String getRawScore() {
        if (maxRawScore.equals(minRawScore)) return maxRawScore.toString();
        else return minRawScore + "-" + maxRawScore;
    }

}
