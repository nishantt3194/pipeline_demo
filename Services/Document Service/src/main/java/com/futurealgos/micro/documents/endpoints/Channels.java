/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.endpoints;

import com.futurealgos.micro.documents.dto.AttachmentsPayload;
import com.futurealgos.micro.documents.utils.constants.InternalChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class Channels {

    @Bean(InternalChannel.ATTACHMENTS_CONNECTOR)
    public MessageChannel attachmentsChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }

    @Bean(InternalChannel.TEST_ATTACHMENTS_CHANNEL)
    public MessageChannel testAttachmentsChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }

    @Bean(InternalChannel.PARTNER_ATTACHMENTS_CHANNEL)
    public MessageChannel partnerAttachmentsChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }


    @Bean(InternalChannel.SINK)
    public MessageChannel sink() {
        return new NullChannel();
    }
}
