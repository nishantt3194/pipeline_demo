/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AttachmentsPayload implements Serializable {
    @JsonProperty("id")
    public String id;
    @JsonProperty("file_name")
    public String fileName;
    @JsonProperty("tag")
    public String tag;
    @JsonProperty("metadata")
    public String metadata;
    @JsonProperty("attached")
    public boolean attached;
}


