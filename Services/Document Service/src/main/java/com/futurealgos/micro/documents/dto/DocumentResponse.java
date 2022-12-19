package com.futurealgos.micro.documents.dto;

public record DocumentResponse(
        String contentType,
        byte[] document
) {

}
