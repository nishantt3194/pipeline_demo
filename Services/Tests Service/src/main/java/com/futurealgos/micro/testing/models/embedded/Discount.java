/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;

/**
 * Package: com.futurealgos.micro.testing.models
 * Project: Prasad Psycho
 * Created On: 13/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
public class Discount {

    public static enum Status {
        ACTIVE, INACTIVE, EXPIRED;
    }

    public static enum Type {
        PERCENTAGE,
        AMOUNT
    }

    private Type type;

    private Status status;

    private Integer value;

    private Instant expiry;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("type", type)
                .append("status", status)
                .append("value", value)
                .append("expiry", expiry)
                .toString();
    }
}
