/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.endpoints.pipelines;

import com.futurealgos.micro.testing.dto.payload.AttachmentsPayload;
import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.utils.constants.InternalChannel;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface PipelineGateway {

    @Gateway(requestChannel = InternalChannel.INIT_STAGING_CONNECTOR)
    void ingest(@Header("submitted") Boolean submitted, @Header("admin") String administrator, StagedTest payload);

    @Gateway(requestChannel = InternalChannel.STAGING_PERSISTENCE_NOTIFIER)
    void tests(StagedTest payload);

    @Gateway(requestChannel = InternalChannel.TEST_ATTACHMENTS_CHANNEL)
    void parseMetadataAttachment(AttachmentsPayload payload);

}
