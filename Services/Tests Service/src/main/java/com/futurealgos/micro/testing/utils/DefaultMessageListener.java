/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class DefaultMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {

    }
}
