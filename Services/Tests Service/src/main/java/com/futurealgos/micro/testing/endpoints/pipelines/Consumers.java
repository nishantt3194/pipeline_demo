/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.pipelines;

import com.futurealgos.micro.testing.dto.payload.AttachmentsPayload;
import com.futurealgos.micro.testing.dto.payload.SubmitProcessingWrapper;
import com.futurealgos.micro.testing.dto.payload.TestsNotification;
import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.services.tests.StagingService;
import com.futurealgos.micro.testing.services.tests.SubmissionService;
import com.futurealgos.micro.testing.services.tests.TestDataService;
import com.futurealgos.micro.testing.utils.constants.InternalChannel;
import com.futurealgos.micro.testing.utils.constants.RabbitQueues;
import com.futurealgos.micro.testing.utils.enums.TestAckType;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.messaging.Message;

@Configuration
public class Consumers {

    @Autowired
    StagingService stagingService;
    @Autowired
    TestDataService testDataService;

    @Autowired
    SubmissionService submissionService;

    @Bean
    public IntegrationFlow stagingInitCheckpoint() {
        return IntegrationFlows.from(InternalChannel.INIT_STAGING_CONNECTOR)
                .channel(InternalChannel.STAGING_MERGER_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow stagingMergerCheckpoint() {
        return IntegrationFlows.from(InternalChannel.STAGING_MERGER_CHANNEL)
                .<StagedTest>handle((payload, headers) -> stagingService.merge(payload))
                .channel(InternalChannel.STAGING_PERSISTENCE_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow stagingPersistence() {
        return IntegrationFlows.from(InternalChannel.STAGING_PERSISTENCE_CHANNEL)
                .<StagedTest>handle((payload, headers) -> stagingService.persist(payload))
                .channel(InternalChannel.STAGING_PERSISTENCE_NOTIFIER)
                .get();
    }

    @Bean
    public IntegrationFlow stagingPersistenceNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.STAGING_PERSISTENCE_NOTIFIER)
                .<StagedTest, TestsNotification>transform(staged ->
                        new TestsNotification(
                                staged.getId(),
                                TestAckType.TEST_SAVED,
                                null,
                                "Test Saved Successfully"))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();
    }

    @Bean
    public IntegrationFlow checkToBeSubmitted() {
        return IntegrationFlows.from(InternalChannel.STAGING_PERSISTENCE_NOTIFIER)
                .route(Message.class,
                        message -> (Boolean) message.getHeaders().get("submitted"),
                        mapping -> mapping
                                .channelMapping(true, InternalChannel.ATTACHMENT_VERIFICATION_CHANNEL)
                                .channelMapping(false, InternalChannel.SINK))
                .get();
    }


    @Bean
    public IntegrationFlow attachmentsVerification() {
        return IntegrationFlows.from(InternalChannel.ATTACHMENT_VERIFICATION_CHANNEL)
                .<StagedTest>handle((payload, headers) -> {
                    payload.evaluateAttachments();
                    return payload;
                })
                .route(StagedTest.class, StagedTest::getAttachmentsCompleted,
                        mapping -> mapping
                                .channelMapping(true, InternalChannel.INIT_CREATION_CHANNEL)
                                .channelMapping(false, InternalChannel.SINK))
                .get();
    }

    @Bean
    public IntegrationFlow submitInitiatedNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.INIT_CREATION_CHANNEL)
                .<StagedTest, TestsNotification>transform(staged ->
                        new TestsNotification(
                                staged.getId(),
                                TestAckType.SUBMIT_INITIATED,
                                null,
                                "Submitting Test"
                        ))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();

    }

    @Bean
    public IntegrationFlow submitTestTransformation() {
        return IntegrationFlows.from(InternalChannel.INIT_CREATION_CHANNEL)
                .<StagedTest>handle((payload, headers) -> {
                    String admin = (String) headers.get("admin");
                    return submissionService.process(payload, admin);
                })
                .channel(InternalChannel.SUBSCALES_CHANNEL)
                .get();

    }

    @Bean
    public IntegrationFlow basicDetailsNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.SUBSCALES_CHANNEL)
                .<SubmitProcessingWrapper, TestsNotification>transform(wrapper ->
                        new TestsNotification(
                                wrapper.payload.getId(),
                                TestAckType.BASIC_DETAILS_SUBMITTED,
                                null,
                                "Basic Details Submitted"
                        ))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();

    }

    @Bean
    public IntegrationFlow subscalesBuilder() {
        return IntegrationFlows.from(InternalChannel.SUBSCALES_CHANNEL)
                .<SubmitProcessingWrapper>handle((payload, headers) ->
                        submissionService.subscaleProcessing(payload))
                .channel(InternalChannel.SCORE_TABLE_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow subscaleBuiltNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.SCORE_TABLE_CHANNEL)
                .<SubmitProcessingWrapper, TestsNotification>transform(wrapper ->
                        new TestsNotification(
                                wrapper.payload.getId(),
                                TestAckType.SUBSCALES_BUILT,
                                null,
                                "Subscales Built"
                        ))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();

    }

    @Bean
    public IntegrationFlow scoreTableBuilder() {
        return IntegrationFlows.from(InternalChannel.SCORE_TABLE_CHANNEL)
                .<SubmitProcessingWrapper>handle((payload, headers) ->
                        submissionService.generateTables(payload))
                .channel(InternalChannel.FINAL_SUBMIT_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow scoreTablesBuiltNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.FINAL_SUBMIT_CHANNEL)
                .<SubmitProcessingWrapper, TestsNotification>transform(wrapper ->
                        new TestsNotification(
                                wrapper.payload.getId(),
                                TestAckType.SCORE_TABLES_GENERATED,
                                null,
                                "Score Tables Generated"
                        ))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();

    }

    @Bean
    public IntegrationFlow finalSubmit() {
        return IntegrationFlows.from(InternalChannel.FINAL_SUBMIT_CHANNEL)
                .<SubmitProcessingWrapper>handle((payload, headers) -> {
                    payload = submissionService.submit(payload);
                    stagingService.delete(payload.payload.getId());
                    return payload;
                })
                .channel(InternalChannel.SUBMIT_NOTIFIER)
                .get();
    }

    @Bean
    public IntegrationFlow finalSubmitNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.SUBMIT_NOTIFIER)
                .<SubmitProcessingWrapper, TestsNotification>transform(wrapper ->
                        new TestsNotification(
                                wrapper.payload.getId(),
                                TestAckType.SUBMIT_SUCCESSFUL,
                                null,
                                "Test Submitted Successfully"
                        ))
                .<TestsNotification>handle((payload, header) -> payload)
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .get();

    }

    @Bean
    public IntegrationFlow attachMetadata(SimpleRabbitListenerContainerFactory factory) {
        SimpleMessageListenerContainer container = factory.createListenerContainer();
        container.addQueueNames(RabbitQueues.TESTS_METADATA_ATTACHMENTS);
        return IntegrationFlows.from(Amqp.inboundAdapter(container))
                .transform(Transformers.fromJson(AttachmentsPayload.class))
                .<AttachmentsPayload>handle((payload, headers) -> stagingService.attach(payload))
                .channel(InternalChannel.ATTACHMENTS_NOTIFIER)
                .get();
    }

    @Bean
    public IntegrationFlow stagingAttachmentNotifier(RabbitTemplate template) {
        return IntegrationFlows.from(InternalChannel.ATTACHMENTS_NOTIFIER)
                .<AttachmentsPayload, TestsNotification>transform(payload ->
                        new TestsNotification(
                                payload.id,
                                payload.attached ? TestAckType.ATTACHMENT_COMPLETE : TestAckType.ATTACHMENT_ERROR,
                                payload.tag,
                                payload.attached ? "Attachment Successful" : "Could not attach object"))
                .handle(Amqp.outboundAdapter(template).routingKey(RabbitQueues.TESTS_INTERNAL_NOTIFICATIONS))
                .channel(InternalChannel.SINK)
                .get();
    }

    @Bean
    public IntegrationFlow persistenceBridge() {
        return IntegrationFlows.from(InternalChannel.ATTACHMENTS_NOTIFIER)
                .<AttachmentsPayload, StagedTest>transform(payload -> stagingService.fetch(payload.id))
                .route(StagedTest.class, StagedTest::getSubmitted,
                        mapping -> mapping
                                .channelMapping(true, InternalChannel.ATTACHMENT_VERIFICATION_CHANNEL)
                                .channelMapping(false, InternalChannel.SINK))
                .get();
    }

}
