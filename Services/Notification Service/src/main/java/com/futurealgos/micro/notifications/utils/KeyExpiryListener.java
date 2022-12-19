/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.utils;


import com.futurealgos.micro.notifications.endpoints.PipelineGateway;
import com.futurealgos.micro.notifications.payloads.SessionNotification;
import com.futurealgos.micro.notifications.utils.constants.CacheNamespaces;
import com.futurealgos.micro.notifications.utils.enums.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Package: com.futurealgos.micro.notifications.utils
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Component
public class KeyExpiryListener implements MessageListener {

    @Autowired
    PipelineGateway gateway;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String id = new String(message.getBody());
        if(id.startsWith(CacheNamespaces.ACTIVE_ASSESSMENT_SESSION)) {
            id = id.split("::")[1];
            Calendar c = Calendar.getInstance();
            c.add(Calendar.SECOND, -2);
            gateway.notifySessionState(new SessionNotification(id, null, SessionState.SUSPENDED, c.getTime()));

        }
    }
}
