/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.endpoints;

import com.futurealgos.micro.notifications.payloads.PartnerNotification;
import com.futurealgos.micro.notifications.payloads.SessionNotification;
import com.futurealgos.micro.notifications.payloads.TestsNotification;
import com.futurealgos.micro.notifications.utils.constants.InternalChannels;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class Channels {

    @Bean(InternalChannels.TESTS_ACK_HANDLER_CHANNEL)
    public MessageChannel internalTestsAckChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(TestsNotification.class);
        return channel;
    }

    @Bean(InternalChannels.PARTNER_ACK_HANDLER_CHANNEL)
    public MessageChannel internalPartnerAckChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(PartnerNotification.class);
        return channel;
    }

    @Bean(InternalChannels.SESSION_STATE_NOTIFICATION_CHANNEL)
    public MessageChannel sessionTracker() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(SessionNotification.class);
        return channel;
    }


    @Bean(InternalChannels.SINK)
    public MessageChannel sink() {
        return new NullChannel();
    }

    @Bean(InternalChannels.LOGGER_CHANNEL)
    public MessageChannel loggerChannel() {
        return new DirectChannel();
    }
}
