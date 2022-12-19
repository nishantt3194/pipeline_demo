/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;

import com.futurealgos.micro.assessments.models.base.Score;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Package: com.futurealgos.micro.assessments.models.embedded
 * Project: Prasad Psycho
 * Created On: 9/7/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Builder
public class InterpretationReport {
    public enum Type {
        OVERALL,
        DIMENSIONAL
    }

    private Type type;

    @Field("heading")
    private String heading;

    @Field("description")
    private String description;

    @Field("actual_score")
    private Integer actualScore;

    @DocumentReference(collection = "score")
    private Score score;
}
