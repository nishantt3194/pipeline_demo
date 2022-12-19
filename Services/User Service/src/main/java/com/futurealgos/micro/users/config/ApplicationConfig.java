/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import io.awspring.cloud.ses.SimpleEmailServiceJavaMailSender;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.http.CacheControl;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    ConnectionFactory factory;

    AWSCredentialsProvider provider;

    @Autowired
    public ApplicationConfig(ConnectionFactory factory, AWSCredentialsProvider provider) {
        Assert.notNull(factory, "ConnectionFactory cannot be null");
        Assert.notNull(provider, "AWSCredentialsProvider cannot be null");

        this.factory = factory;
        this.provider = provider;
    }

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }


    @Bean
    public AmazonSimpleEmailService emailService() {
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(provider)
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }

    @Bean
    public JavaMailSender mailSender(AmazonSimpleEmailService emailService) {
        return new SimpleEmailServiceJavaMailSender(emailService);
    }

    @Bean
    RabbitTemplate template() {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public MappingMongoConverter mongoConverter(MongoDatabaseFactory factory, MongoMappingContext mongoMappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);

        return mongoConverter;
    }


    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Accept", "X-Requested-With", "Authorization")
                .allowCredentials(false)
                .maxAge(3600L);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(
                        "/images/**",
                        "/css/**",
                        "/js/**").addResourceLocations("classpath:/static/assets/images/",
                        "classpath:/static/assets/css/",
                        "classpath:/static/assets/js/"
                )
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS));

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry
                .addViewController("").setViewName("health");
    }

}
