/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.dto;

import com.futurealgos.micro.documents.dao.DocumentPointer;
import com.futurealgos.micro.documents.utils.specs.dto.ListResponse;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinimalDocument extends ListResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1955247489884915489L;

    @NotNull
    public String id;
    @NotNull
    public String name;
    public String description;
    @NotNull
    public String fileId;

    public String status;

    public String date;

    public static MinimalDocument build(DocumentPointer documentPointer) {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(documentPointer.getCreatedOn());
        return MinimalDocument.builder()
                .date(date)
                .status(documentPointer.getStatus().toString())
                .build();
    }

}
