/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.docs;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record DocPayload(
        @JsonProperty("id") @NotNull String id,
        @JsonProperty("name") @NotNull String name,
        @JsonProperty("remark") String remark,
        @JsonProperty("tag") String tag,
        @JsonProperty("initiator") String initiator,
        @JsonProperty("category") String category) implements Serializable {

}
