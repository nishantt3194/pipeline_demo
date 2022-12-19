/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.endpoints;

import com.futurealgos.micro.notifications.payloads.PartnerNotification;
import com.futurealgos.micro.notifications.payloads.TestsNotification;
import com.futurealgos.micro.notifications.services.Notifier;
import com.futurealgos.micro.notifications.utils.constants.InternalChannels;
import com.futurealgos.micro.notifications.utils.constants.RabbitQueues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.stereotype.Service;

@Service
public class Consumers {

    Logger logger = LoggerFactory.getLogger(Consumers.class);

    @Autowired
    ConnectionFactory factory;

    @Autowired
    Notifier notifier;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    PipelineGateway gateway;

    @Bean
    public IntegrationFlow internalTestAckAdapter(SimpleRabbitListenerContainerFactory factory) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        container.addQueueNames(RabbitQueues.TESTS_INTERNAL);
        return IntegrationFlows.from(Amqp.inboundAdapter(container))
                .transform(Transformers.fromJson(TestsNotification.class))
                .channel(InternalChannels.TESTS_ACK_HANDLER_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow internalPartnerAckAdapter(SimpleRabbitListenerContainerFactory factory) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        container.addQueueNames(RabbitQueues.PARTNERS_INTERNAL);
        return IntegrationFlows.from(Amqp.inboundAdapter(container))
                .transform(Transformers.fromJson(PartnerNotification.class))
                .channel(InternalChannels.PARTNER_ACK_HANDLER_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow internalTestAckHandler() {
        return IntegrationFlows.from(InternalChannels.TESTS_ACK_HANDLER_CHANNEL)
                .<TestsNotification>handle((payload, headers) -> {
                    logger.info(payload.toString());
                    notifier.broadcastToDestination(String.format("/wss/tests/create/%s", payload.id()), payload);
                    return null;
                })
                .channel(InternalChannels.SINK)
                .get();
    }

    @Bean
    public IntegrationFlow internalPartnerAckHandler() {
        return IntegrationFlows.from(InternalChannels.PARTNER_ACK_HANDLER_CHANNEL)
                .<TestsNotification>handle((payload, headers) -> {
                    logger.info(payload.toString());
                    notifier.broadcastToDestination(String.format("/wss/partners/docs/%s", payload.id()), payload);
                    return null;
                })
                .channel(InternalChannels.SINK)
                .get();
    }


    @Bean
    public IntegrationFlow assessmentSessionTracker(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannels.SESSION_STATE_NOTIFICATION_CHANNEL)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.ASSESSMENT_INTERNAL_NOTIFICATIONS))
                .get();
    }

}
