/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.endpoints;

import com.futurealgos.micro.documents.utils.constants.InternalChannel;
import com.futurealgos.micro.documents.utils.constants.RabbitQueues;
import com.futurealgos.micro.documents.utils.enums.DocCategory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;

@Configuration
public class Consumers {

    @Bean
    public IntegrationFlow attachmentsConnector() {
        return IntegrationFlows.from(InternalChannel.ATTACHMENTS_CONNECTOR)
                .route(Message.class,
                        message -> (String) message.getHeaders().get("category"),
                        mapping -> mapping
                                .channelMapping(DocCategory.TEST_DOCUMENT.getTag(), InternalChannel.TEST_ATTACHMENTS_CHANNEL)
                                .channelMapping(DocCategory.PARTNER_DOCUMENT.getTag(), InternalChannel.PARTNER_ATTACHMENTS_CHANNEL))
                .get();
    }

    @Bean
    public IntegrationFlow testsAttachmentNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.TEST_ATTACHMENTS_CHANNEL)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_METADATA_ATTACHMENTS))
                .channel(InternalChannel.SINK)
                .get();
    }

    @Bean
    public IntegrationFlow partnerAttachmentNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.PARTNER_ATTACHMENTS_CHANNEL)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.PARTNERS_METADATA_ATTACHMENTS))
                .channel(InternalChannel.SINK)
                .get();
    }
}
