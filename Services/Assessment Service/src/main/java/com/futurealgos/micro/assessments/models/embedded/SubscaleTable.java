package com.futurealgos.micro.assessments.models.embedded;

import com.futurealgos.micro.assessments.models.base.Score;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SubscaleTable {

    private String tableName;

    private List<Score> score;


}
