/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.endpoints;

import com.futurealgos.micro.documents.dto.AttachmentsPayload;
import com.futurealgos.micro.documents.utils.constants.InternalChannel;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface ChannelGateway {

    @Gateway(requestChannel = InternalChannel.ATTACHMENTS_CONNECTOR)
    void attachMetadata(@Header("category") String category, AttachmentsPayload payload);
}
