/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.config;

import com.futurealgos.micro.assessments.utils.NormsReader;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.config
 * Project: Prasad Psycho
 * Created On: 28/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    ConnectionFactory factory;

    @Autowired
    public ApplicationConfig(ConnectionFactory factory) {
        this.factory = factory;
    }

    @Bean
    RabbitTemplate template() {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    @Primary
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory factory, MongoMappingContext mongoMappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mongoConverter.setCustomConversions(conversions());
        return mongoConverter;
    }

    MongoCustomConversions conversions() {
        return new MongoCustomConversions(List.of(new NormsReader()));
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Accept", "X-Requested-With", "Authorization")
                .allowCredentials(false)
                .maxAge(3600L);
    }
}
