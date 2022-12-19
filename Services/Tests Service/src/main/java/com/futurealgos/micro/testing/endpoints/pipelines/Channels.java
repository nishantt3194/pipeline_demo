/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.pipelines;

import com.futurealgos.micro.testing.dto.payload.AttachmentsPayload;
import com.futurealgos.micro.testing.dto.payload.SubmitProcessingWrapper;
import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.utils.constants.InternalChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.channel.NullChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.concurrent.Executors;

@Configuration
public class Channels {


    @Bean(name = InternalChannel.INIT_STAGING_CONNECTOR)
    public MessageChannel stagingInitConnector() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(name = InternalChannel.STAGING_MERGER_CHANNEL)
    public MessageChannel stagingMergerChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(InternalChannel.STAGING_PERSISTENCE_CHANNEL)
    MessageChannel stagingPersistenceConnector() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(InternalChannel.STAGING_PERSISTENCE_NOTIFIER)
    MessageChannel stagingPersistenceNotifier() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(InternalChannel.ATTACHMENTS_NOTIFIER)
    MessageChannel attachementNotifierChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }

    @Bean(InternalChannel.ATTACHMENT_VERIFICATION_CHANNEL)
    MessageChannel attachementVerificationChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(InternalChannel.TEST_ATTACHMENTS_CHANNEL)
    MessageChannel testAttachmentsChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(AttachmentsPayload.class);
        return channel;
    }

//    @Bean(InternalChannel.CREATION_BRIDGE)
//    MessageChannel submitBridge() {
//        FluxMessageChannel channel = MessageChannels.flux().get();
//        channel.setDatatypes(StagedTest.class);
//        return channel;
//    }

    @Bean(InternalChannel.INIT_CREATION_CHANNEL)
    MessageChannel initCreationChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(StagedTest.class);
        return channel;
    }

    @Bean(InternalChannel.SUBSCALES_CHANNEL)
    MessageChannel subscaleBuildingChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(SubmitProcessingWrapper.class);
        return channel;
    }

    @Bean(InternalChannel.SCORE_TABLE_CHANNEL)
    MessageChannel scoreTableBuildingChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(SubmitProcessingWrapper.class);
        return channel;
    }

    @Bean(InternalChannel.FINAL_SUBMIT_CHANNEL)
    MessageChannel finalSubmitChannel() {
        PublishSubscribeChannel channel = MessageChannels.publishSubscribe(Executors.newCachedThreadPool()).get();
        channel.setDatatypes(SubmitProcessingWrapper.class);
        return channel;
    }

    @Bean(InternalChannel.SUBMIT_NOTIFIER)
    MessageChannel submitNotififerChannel() {
        FluxMessageChannel channel = MessageChannels.flux().get();
        channel.setDatatypes(SubmitProcessingWrapper.class);
        return channel;
    }

    @Bean(InternalChannel.INCOMPLETE_ERROR_NOTIFICATION_CHANNEL)
    MessageChannel incompleteErrorNotificationChannel() {
        return MessageChannels.executor(Executors.newCachedThreadPool()).get();
    }

    @Bean(name = InternalChannel.SINK)
    MessageChannel sink() {
        return new NullChannel();
    }

    //    Default Poller ---------------------------------------------------------------------------------------------------
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaultPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(10000));
        return pollerMetadata;
    }
}
