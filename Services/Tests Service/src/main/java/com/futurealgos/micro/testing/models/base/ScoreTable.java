/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.base;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.futurealgos.micro.testing.models.embedded.Score;
import com.futurealgos.micro.testing.models.embedded.ScoreNormMap;
import com.futurealgos.micro.testing.models.root.BaseEntity;
import com.futurealgos.micro.testing.utils.enums.Mode;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "score_table")
public class ScoreTable extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2963344127596318854L;
    @Field("table_name")
    private String name;

    @Field("norm_mapping")
    @Builder.Default
    private Set<ScoreNormMap> normMap = new HashSet<>();

    @DocumentReference(collection = "subscale")
    private Subscale subscale;

    @Field
    private Mode mode;

    @Field
    @Builder.Default
    @DocumentReference(collection = "score")
    private Set<Score> scores = new HashSet<>();

    @Field
    @DocumentReference(collection = "test")
    private Test test;

    public static ScoreTable defaultTable() {
        return ScoreTable.builder()
                .id(new ObjectId())
                .name("Overall")
                .mode(Mode.DEFAULT)
                .build();
    }

    public static ScoreTable shell() {
        return ScoreTable.builder()
                .id(new ObjectId())
                .build();
    }

    @JsonBackReference
    public Test getTest() {
        return test;
    }

    @JsonManagedReference
    public Set<Score> getScores() {
        return scores;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("normMap", normMap)
                .append("subscale", subscale)
                .append("mode", mode)
                .append("scores", scores)
                .append("test", test)
                .toString();
    }

}
