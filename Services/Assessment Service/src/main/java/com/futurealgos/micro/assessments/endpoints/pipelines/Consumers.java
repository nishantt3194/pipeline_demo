/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.pipelines;

import com.futurealgos.micro.assessments.dto.payload.SessionNotification;
import com.futurealgos.micro.assessments.services.ExecutionService;
import com.futurealgos.micro.assessments.utils.constants.InternalChannels;
import com.futurealgos.micro.assessments.utils.constants.RabbitQueues;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.stereotype.Service;

/**
 * Package: com.futurealgos.micro.assessments.endpoints.pipelines
 * Project: Prasad Psycho
 * Created On: 03/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class Consumers {

    @Autowired
    ExecutionService executionService;

    @Bean
    public IntegrationFlow sessionStateObserver(SimpleRabbitListenerContainerFactory factory) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        container.addQueueNames(RabbitQueues.ASSESSMENT_INTERNAL_NOTIFICATIONS);

        return IntegrationFlows.from(Amqp.inboundAdapter(container))
                .transform(Transformers.fromJson(SessionNotification.class))
                .<SessionNotification>handle((payload, headers) -> executionService.updateSessionState(payload))
                .channel(InternalChannels.SINK)
                .get();
    }
}
