/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.utils.constants;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Package: com.futurealgos.micro.assessments.utils.constants
 * Project: Prasad Psycho
 * Created On: 03/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public class RabbitQueues {
    public static final String ASSESSMENT_INTERNAL_NOTIFICATIONS = "notifications.internal.assessments";

    private RabbitQueues() {
    }

    public static String[] queues() throws RuntimeException {
        return Arrays.stream(RabbitQueues.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field -> {
                    try {
                        return (String) field.get(String.class);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).toList().toArray(new String[0]);
    }
}
