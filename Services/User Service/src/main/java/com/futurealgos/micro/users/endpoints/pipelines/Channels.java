/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.endpoints.pipelines;

import com.futurealgos.micro.users.dto.payload.pipeline.AttachmentsPayload;
import com.futurealgos.micro.users.models.base.User;
import com.futurealgos.micro.users.utils.InternalChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

import java.util.concurrent.Executors;

@Configuration
public class Channels {

    @Bean(InternalChannel.ATTACHMENTS_NOTIFIER)
    MessageChannel attachmentNotifierChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }

    @Bean(InternalChannel.ATTACHMENT_VERIFICATION_CHANNEL)
    MessageChannel attachmentVerificationChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(User.class);
        return channel;
    }

    @Bean(InternalChannel.PARTNER_ATTACHMENTS_CHANNEL)
    MessageChannel testAttachmentsChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }


    @Bean(InternalChannel.INCOMPLETE_ERROR_NOTIFICATION_CHANNEL)
    MessageChannel incompleteErrorNotificationChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }

    @Bean(name = InternalChannel.SINK)
    MessageChannel sink() {
        return new NullChannel();
    }

}
