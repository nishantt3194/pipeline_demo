/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.endpoints;

import com.futurealgos.micro.notifications.payloads.SessionNotification;
import com.futurealgos.micro.notifications.payloads.TestsNotification;
import com.futurealgos.micro.notifications.utils.constants.InternalChannels;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface PipelineGateway {


    @Gateway(requestChannel = InternalChannels.TESTS_ACK_HANDLER_CHANNEL)
    void parseTestAcknowledgement(TestsNotification payload);

    @Gateway(requestChannel = InternalChannels.SESSION_STATE_NOTIFICATION_CHANNEL)
    void notifySessionState(SessionNotification payload);
}
