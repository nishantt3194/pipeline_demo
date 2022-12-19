/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.utils.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Service;

/**
 * Package: com.futurealgos.micro.notifications.utils.constants
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class LocalConstants implements ConstantsService {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private Integer redisPort;

    Logger logger = LoggerFactory.getLogger(LocalConstants.class);

    @Override
    public RedisStandaloneConfiguration redisFactory() {
        logger.info("DEV Mode - Using localhost Redis...");
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);

        return configuration;
    }
}
