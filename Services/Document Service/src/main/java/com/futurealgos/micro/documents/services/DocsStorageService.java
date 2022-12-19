/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.services;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurealgos.micro.documents.dao.DocumentMetadata;
import com.futurealgos.micro.documents.dao.DocumentPointer;
import com.futurealgos.micro.documents.dto.AttachmentsPayload;
import com.futurealgos.micro.documents.dto.DocumentResponse;
import com.futurealgos.micro.documents.dto.MetadataDto;
import com.futurealgos.micro.documents.dto.MinimalDocument;
import com.futurealgos.micro.documents.endpoints.ChannelGateway;
import com.futurealgos.micro.documents.repos.MetadataRepo;
import com.futurealgos.micro.documents.repos.PointerRepo;
import com.futurealgos.micro.documents.utils.enums.DocCategory;
import com.futurealgos.micro.documents.utils.enums.Status;
import com.futurealgos.micro.documents.utils.specs.services.ListSpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DocsStorageService implements ListSpec<DocumentPointer, MinimalDocument> {

    private final String bucket = "pp-object-store";

    @Autowired
    ChannelGateway gateway;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private PointerRepo pointerRepo;

    @Autowired
    private MetadataRepo metaDataRepo;


    public String upload(String type, String id, String tag, Boolean replace, MultipartFile file) {
        File object = convertMultiPartFileToFile(file);
        String extn = FilenameUtils.getExtension(file.getOriginalFilename());

        DocumentPointer pointer
                = DocumentPointer.builder()
                .id(new ObjectId())
                .status(Status.CREATED)
                .extension(extn)
                .category(DocCategory.fromName(type))
                .build();

        pointer.setFileName(String.format("%s.%s", pointer.getId().toHexString(), pointer.getExtension()));
        String path = pointer.buildPath(extn);

        s3Client.putObject(bucket, path, object);
        object.delete();

        pointer = pointerRepo.save(pointer);

        buildMetadata(pointer, id, tag, replace);

        return pointer.getId().toHexString();
    }

    public void buildMetadata(DocumentPointer pointer, String id, String tag, Boolean replace) {
        DocumentMetadata metadata = metaDataRepo.findByTag(tag).orElse(null);

        if (metadata != null && replace) remove(metadata.getPointer());

        if (metadata == null) {
            Map<String, String> attributes = Map.of(
                    "category", pointer.getCategory().getTag(),
                    "associated_id", id
            );
            metadata = DocumentMetadata.builder()
                    .pointer(pointer)
                    .tag(tag)
                    .attributes(attributes)
                    .documentVersion(1)
                    .history(List.of(pointer.getId().toHexString()))
                    .build();
        } else if (!replace) {
            metadata.setPointer(pointer);
            metadata.getHistory().add(pointer.getId().toHexString());
        } else {
            metadata.setPointer(pointer);
            metadata.setHistory(List.of(pointer.getId().toHexString()));
        }

        metadata = metaDataRepo.save(metadata);

        AttachmentsPayload attachmentsPayload = AttachmentsPayload.builder()
                .id(id)
                .tag(tag)
                .fileName(pointer.getFileName())
                .metadata(metadata.getId().toHexString())
                .attached(false)
                .build();


        gateway.attachMetadata(pointer.getCategory().getTag(), attachmentsPayload);
    }

    public void remove(DocumentPointer pointer) {
        if (pointer == null || pointer.getId() == null) return;
        s3Client.deleteObject(bucket, pointer.getPath());
        pointerRepo.delete(pointer);
    }

    //tbd
    public byte[] info(String fileId) {
        S3Object s3Object = s3Client.getObject(bucket, fileId);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileId) {
        s3Client.deleteObject(bucket, fileId);
        return fileId + " removed.....";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting MultipartFile to File");
        }
        return convertedFile;
    }


    @Transactional(readOnly = true)
    public List<MinimalDocument> view(String id) {
        return pointerRepo.findById(new
                        ObjectId(id))
                .stream()
                .map(MinimalDocument::build)
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public List<MinimalDocument> search(String name) {
//        return pointerRepo.findByTitleIgnoreCaseContains(name)
//                .stream()
//                .map(MinimalDocument::build)
//                .collect(Collectors.toList());

        return new ArrayList<>();
    }

    @Override
    public Page<MinimalDocument> list(Pageable pageable) {
        return pointerRepo.findAll(pageable)
                .map(MinimalDocument::build);
    }

    public MetadataDto convert(String json) throws IOException {

        InputStream inJson = MetadataDto.class.getResourceAsStream(json);

        return new ObjectMapper().readValue(inJson, MetadataDto.class);
    }

    ;


//    public DocumentMetadata attachMetaData(MetadataDto payload) throws IOException {
//        DocumentPointer pointer = pointerRepo.findById(new ObjectId(payload.getPointer()))
//                .orElse(null);
//
//        DocumentMetadata metadata = DocumentMetadata.builder()
//                .attributes(payload.getTags())
//                .tag(payload.getPool())
//
//                .build();
//
//        if (pointer == null)
//            return null;
//        metadata.setPointer(pointer);
//        metaDataRepo.save(metadata);
//        return metadata;
//
//    }

    public DocumentResponse viewDocument(String id) throws IOException {
        DocumentMetadata metaData = metaDataRepo.findById(new ObjectId(id)).orElse(null);


        if (metaData == null) {
            throw new NotFoundException("Document not found");
        }
        String extension = metaData.getPointer().getExtension();
        if (metaData.getPointer().getCategory() == DocCategory.PARTNER_DOCUMENT) {

            return new DocumentResponse(extension, info("partners/" + metaData.getPointer().getFileName()));

        }
        if (metaData.getPointer().getCategory() == DocCategory.TEST_DOCUMENT) {

            return new DocumentResponse(extension, info("tests/" + metaData.getPointer().getFileName()));

        }
        else
            return null;

    }



}
