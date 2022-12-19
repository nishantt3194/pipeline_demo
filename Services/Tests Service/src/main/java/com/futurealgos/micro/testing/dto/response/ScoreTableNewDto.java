/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response;

import com.futurealgos.micro.testing.dto.response.tests.ScoreInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScoreTableNewDto {
    private String identifier;
    private String name;
    private List<ScoreInfo> scores;
}
