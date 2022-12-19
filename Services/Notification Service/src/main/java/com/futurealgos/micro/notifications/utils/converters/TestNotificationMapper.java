/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.utils.converters;

import com.futurealgos.micro.notifications.payloads.TestsNotification;
import org.springframework.integration.support.json.JsonObjectMapper;

import java.io.IOException;

public class TestNotificationMapper implements JsonObjectMapper<String, TestsNotification> {

    @Override
    public String toJson(Object value) throws IOException {
        return "";
    }


}
