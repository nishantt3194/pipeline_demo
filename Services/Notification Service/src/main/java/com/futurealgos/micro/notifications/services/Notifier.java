/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.services;

import com.futurealgos.micro.notifications.endpoints.PipelineGateway;
import com.futurealgos.micro.notifications.payloads.SessionNotification;
import com.futurealgos.micro.notifications.utils.constants.CacheNamespaces;
import com.futurealgos.micro.notifications.utils.enums.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Notifier {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    PipelineGateway gateway;

    public void updateSessionState(String id, String ip) {
        Cache sessionCache = cacheManager.getCache(CacheNamespaces.ACTIVE_ASSESSMENT_SESSION);
        assert sessionCache != null;
        Cache.ValueWrapper dataWrapper = sessionCache.get(id);
        if(dataWrapper == null || dataWrapper.get() == null) gateway.notifySessionState(new SessionNotification(id, ip, SessionState.CONNECTED, new Date()));

        sessionCache.put(id, "");
    }

    public void broadcast(Object payload) {
        template.convertAndSend(payload);
    }

    public void broadcastToDestination(String destination, Object payload) {
        template.convertAndSend(destination, payload);
    }

    public void notifyUser(String user, String destination, Object payload) {
        template.convertAndSendToUser(user, destination, payload);

    }
}
