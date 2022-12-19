/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.futurealgos.micro.testing.utils.enums.TestAckType;

import java.io.Serializable;

public record TestsNotification(
        @JsonProperty("id") String id,
        @JsonProperty("type") TestAckType type,
        @JsonProperty("tag") String tag,
        @JsonProperty("message") String message) implements Serializable {

}
