/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.exec;

import com.futurealgos.micro.assessments.models.base.Assignee;
import com.futurealgos.micro.assessments.models.base.Test;

import java.io.Serializable;
import java.time.Duration;

/**
 * Package: com.futurealgos.micro.assessments.dto.response.exec
 * Project: Prasad Psycho
 * Created On: 09/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record SessionExchange(
        String id,
        Long elapsedTime,
        Long timeLimit) implements Serializable {

    public static SessionExchange build(Assignee assignee, Test test) {
        return new SessionExchange(
                assignee.getId().toHexString(),
                assignee.fetchElapsedTime(),
                Duration.ofMinutes(test.getTotalTime()).toMillis()
        );
    }

    public static SessionExchange shell() {
        return new SessionExchange("", 0L, 0L);
    }
}
