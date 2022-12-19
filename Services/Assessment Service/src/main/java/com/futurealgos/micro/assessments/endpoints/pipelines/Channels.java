/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.endpoints.pipelines;

import com.futurealgos.micro.assessments.utils.constants.InternalChannels;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.NullChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

/**
 * Package: com.futurealgos.micro.assessments.endpoints.pipelines
 * Project: Prasad Psycho
 * Created On: 03/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class Channels {


    @Bean(name = InternalChannels.SINK)
    public MessageChannel sink() {
        return new NullChannel();
    }
}
