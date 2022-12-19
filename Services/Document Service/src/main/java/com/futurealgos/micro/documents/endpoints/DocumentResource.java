/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.endpoints;

import com.futurealgos.micro.documents.dto.DocumentResponse;
import com.futurealgos.micro.documents.dto.MinimalDocument;
import com.futurealgos.micro.documents.services.DocsStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class DocumentResource {

    @Autowired
    private DocsStorageService docsStorageService;

    @GetMapping(value = "/health")
    @ResponseStatus(HttpStatus.OK)
    public String serverRunningCheck() {
        return "Server is running";
    }

    @PostMapping("/upload/{type}/{id}/{tag}")
    public ResponseEntity<String> uploadFile(
            @PathVariable("tag") String tag,
            @PathVariable("id") String id,
            @PathVariable("type") String type,
            @RequestParam("replace") Boolean replaceOld,
            @RequestParam(value = "file") MultipartFile file) {
        String docID = docsStorageService.upload(type, id, tag, replaceOld, file);
        return ResponseEntity.accepted().body(docID);

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> download(@PathVariable("id") String fileId) throws IOException {
        DocumentResponse newResponse = docsStorageService.viewDocument(fileId);

        byte[] doc = newResponse.document();
        HttpHeaders headers = new HttpHeaders();
        if(Objects.equals(newResponse.contentType(), "jpg") || Objects.equals(newResponse.contentType(), "jpeg")){
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(doc,headers,HttpStatus.OK);
        }
        if(Objects.equals(newResponse.contentType(), "png")){
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(doc,headers,HttpStatus.OK);
        }
        if(Objects.equals(newResponse.contentType(), "pdf")){
            headers.setContentType(MediaType.APPLICATION_PDF);
            return new ResponseEntity<>(doc,headers,HttpStatus.OK);
        }

        else {
            return null;
        }

    }

    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        return docsStorageService.deleteFile(fileName);
    }

    @GetMapping("/list")
    public Page<MinimalDocument> documentList(@RequestParam("offset") int offset, @RequestParam int size) {

        return docsStorageService.list(PageRequest.of(offset, size));
    }

    @GetMapping("/search")
    public List<MinimalDocument> documentSearch(@RequestParam("file") String name) {
        return docsStorageService.search(name);
    }


}
