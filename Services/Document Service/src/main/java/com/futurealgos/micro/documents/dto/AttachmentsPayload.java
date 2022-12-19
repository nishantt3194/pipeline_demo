/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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

