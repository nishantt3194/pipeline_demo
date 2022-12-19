/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.payload;

import com.futurealgos.micro.assessments.utils.enums.LinkType;
import com.futurealgos.micro.assessments.utils.enums.Type;

import java.io.Serializable;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.dto.payload
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record NewAssessment(
        LinkType linkType,
        Type scheduleType,
        Integer limit,
        boolean bounded,
        String start,
        String end,
        String test,
        String group,
        List<String> examinees) implements Serializable { }
