/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.config;

import com.futurealgos.micro.notifications.utils.IPEnricher;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableRabbit
@Configuration
@EnableWebSocketMessageBroker
public class NotificationConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/resp");
        registry.setApplicationDestinationPrefixes("/ws");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss")
                .addInterceptors(new IPEnricher())
                .setAllowedOrigins(
                        "http://127.0.0.1:4200",
                        "https://admin.prasadpsycho.industrialdata.in",
                        "https://partner.prasadpsycho.industrialdata.in",
                        "http://127.0.0.1:4401",
                        "http://127.0.0.1:4200/",
                        "https://admin.prasadpsycho.industrialdata.in/",
                        "https://partner.prasadpsycho.industrialdata.in/",
                        "http://127.0.0.1:4401/",
                        "chrome-extension://pfdhoblngboilpfeibdedpjgfnlcodoo")
                .withSockJS();
    }

    @Bean
    public RabbitAdmin amqpAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    RabbitTemplate template(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
