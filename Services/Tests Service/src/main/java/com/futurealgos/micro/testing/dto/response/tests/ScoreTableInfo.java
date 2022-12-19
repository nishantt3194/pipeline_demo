/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.ScoreTable;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class ScoreTableInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4258797047791378630L;
    private String identifier;
    private String name;

    private List<ScoreInfo> scores;


    public static ScoreTableInfo build(ScoreTable table) {
        return ScoreTableInfo.builder()
                .identifier(table.getId().toHexString())
                .name(table.getName())
                .scores(table.getScores().stream().map(ScoreInfo::build).toList())
                .build();
    }
}
