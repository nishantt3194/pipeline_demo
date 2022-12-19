/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.config;

import com.futurealgos.micro.notifications.utils.KeyExpiryListener;
import com.futurealgos.micro.notifications.utils.constants.CacheNamespaces;
import com.futurealgos.micro.notifications.utils.constants.ConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Package: com.futurealgos.micro.notifications.config
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@EnableCaching
@Configuration
public class RedisConfig {

    @Autowired
    ConstantsService constants;

    @Autowired
    KeyExpiryListener expiryListener;

    @Bean
    public LettuceConnectionFactory factory() {
        RedisStandaloneConfiguration configuration = constants.redisFactory();
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheConfiguration cacheConfig() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1));
    }

    private Map<String, RedisCacheConfiguration> cacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put(CacheNamespaces.ACTIVE_ASSESSMENT_SESSION,
                RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))
                .entryTtl(Duration.ofSeconds(2)));

        return configurations;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(cacheConfig())
                .withInitialCacheConfigurations(cacheConfigurations())
                .build();
    }

    @Bean
    RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(expiryListener, new PatternTopic("__keyevent@*__:expired"));
        return container;
    }

}
