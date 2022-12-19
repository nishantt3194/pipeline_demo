/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

/**
 * Package: com.futurealgos.micro.users.models.embedded
 * Project: Prasad Psycho
 * Created On: 15/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/

@Getter
@Setter
@Builder
public class DocRequestChat implements Serializable, Comparable<DocRequestChat> {

    public enum Sender {
        ADMIN, USER;
    }

    public Sender sender;

    public String message;

    public Instant timestamp;

    @Override
    public int compareTo(DocRequestChat o) {
        return timestamp.compareTo(o.timestamp);
    }
}
