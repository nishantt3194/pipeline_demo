/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.micro.notifications.utils.enums.PartnerAckType;

import java.io.Serializable;

public record PartnerNotification(@JsonProperty("id") String id,
                                  @JsonProperty("type") PartnerAckType type,
                                  @JsonProperty("tag") String tag,
                                  @JsonProperty("message") String message) implements Serializable {
}
