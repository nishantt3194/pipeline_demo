/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.endpoints.pipelines;

import com.futurealgos.micro.users.dto.payload.pipeline.AttachmentsPayload;
import com.futurealgos.micro.users.dto.payload.pipeline.PartnerNotification;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.service.UserService;
import com.futurealgos.micro.users.utils.InternalChannel;
import com.futurealgos.micro.users.utils.RabbitQueues;
import com.futurealgos.micro.users.utils.enums.PartnerAckType;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;

@Configuration
public class Consumers {

    @Bean
    public IntegrationFlow attachMetadata(SimpleRabbitListenerContainerFactory factory, UserService userService) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        container.addQueueNames(RabbitQueues.PARTNER_METADATA_ATTACHMENTS);
        return IntegrationFlows.from(Amqp.inboundAdapter(container))
                .transform(Transformers.fromJson(AttachmentsPayload.class))
                .<AttachmentsPayload>handle((payload, headers) -> {
                    try {
                        return userService.attach(payload);
                    } catch (InvalidIdentifierFormat e) {
                        payload.attached = false;
                        return payload;
                    }
                })
                .channel(InternalChannel.ATTACHMENTS_NOTIFIER)
                .get();
    }

    @Bean
    public IntegrationFlow attachmentNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.ATTACHMENTS_NOTIFIER)
                .<AttachmentsPayload, PartnerNotification>transform(payload ->
                        new PartnerNotification(
                                payload.id,
                                payload.attached ? PartnerAckType.ATTACHMENT_COMPLETE : PartnerAckType.ATTACHMENT_ERROR,
                                payload.tag,
                                payload.attached ? "Attachment Successful" : "Could not attach object"))
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.PARTNER_INTERNAL_NOTIFICATIONS))
                .channel(InternalChannel.SINK)
                .get();
    }

//    @Bean
//    public IntegrationFlow attachmentNotificationTerminator() {
//        return IntegrationFlows.from(InternalChannel.ATTACHMENTS_NOTIFIER)
//                .channel(InternalChannel.SINK)
//                .get();
//    }

}
