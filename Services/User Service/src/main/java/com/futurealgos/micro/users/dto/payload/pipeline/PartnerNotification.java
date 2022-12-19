/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.micro.users.utils.enums.PartnerAckType;

public record PartnerNotification(

        @JsonProperty("id") String id,
        @JsonProperty("type")
        PartnerAckType type,
        @JsonProperty("tag") String tag,
        @JsonProperty("remark") String message) {
}
