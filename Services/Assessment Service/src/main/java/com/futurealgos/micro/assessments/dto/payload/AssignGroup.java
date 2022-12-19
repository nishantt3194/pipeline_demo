package com.futurealgos.micro.assessments.dto.payload;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssignGroup {
    public String group;
    public List<String> examinees;

}
