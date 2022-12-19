/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.endpoints;

import com.futurealgos.micro.notifications.payloads.SessionHeartbeat;
import com.futurealgos.micro.notifications.services.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * Package: com.futurealgos.micro.notifications.endpoints
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@Controller
public class ExposedEndpoints {

    @Autowired
    Notifier notifier;

    @MessageMapping("/assessments/heartbeat")
    public void heartbeat(@Payload SessionHeartbeat heartbeat, SimpMessageHeaderAccessor accessor) {
        String ip = (String) accessor.getSessionAttributes().get("ip_address");
        notifier.updateSessionState(heartbeat.id, ip);
    }
}
