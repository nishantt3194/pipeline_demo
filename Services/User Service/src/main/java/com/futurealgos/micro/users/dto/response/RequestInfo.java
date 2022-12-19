/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import com.futurealgos.micro.users.models.base.DocRequest;
import com.futurealgos.micro.users.models.embedded.DocRequestChat;
import com.futurealgos.micro.users.utils.enums.Status;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.MediaType;

import java.util.Set;

/**
 * Package: com.futurealgos.micro.users.dto.response
 * Project: Prasad Psycho
 * Created On: 15/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Builder
public class RequestInfo {

    public String id;
    public String type;
    public String name;
    public String fileName;
    public String tag;
    public String metadata;
    public Status status;
    public String fileType;
    public Set<DocRequestChat> chat;


    public static RequestInfo fromDocRequest(DocRequest request){


        return RequestInfo.builder()
                .id(request.getId().toHexString())
                .type(request.getType())
                .name(request.getName())
                .fileName(request.getFileName())
                .tag(request.getTag())
                .metadata(request.getMetadata() != null ? request.getMetadata().getId().toHexString() : null)
                .fileType(request.getMetadata() != null && request.getMetadata().getPointer() != null ?
                        translateContentType(request.getMetadata().getPointer().getExtension()): null)
                .status(request.getStatus())
                .chat(request.getChat())
                .build();
    }

    private static String translateContentType(String extension) {
        switch (extension) {
            case "jpg": return MediaType.IMAGE_JPEG_VALUE;
            case "jpeg": return MediaType.IMAGE_JPEG_VALUE;
            case "png": return MediaType.IMAGE_PNG_VALUE;
            case "pdf": return MediaType.APPLICATION_PDF_VALUE;
            default: return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
