/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.utils.constants;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public final class RabbitQueues {
    public static final String TESTS_METADATA_ATTACHMENTS = "queue.tests.attachments";
    public static final String PARTNERS_METADATA_ATTACHMENTS = "queue.partners.attachments";


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
