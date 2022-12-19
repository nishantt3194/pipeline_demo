/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.models.base.Subscale;
import com.futurealgos.micro.testing.utils.enums.Mode;
import com.futurealgos.micro.testing.utils.enums.TimingType;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.List;

public record NewSubscale(
        @NotNull String name,
        String description,
        String instructions,
        @NotNull TimingType timing,
        @NotNull Mode type,
        Integer time,
        List<NewQuestion> questions) implements Comparable {

    public Subscale convert() {
        return shell().build();
    }

    public Subscale.SubscaleBuilder shell() {
        return Subscale.builder()
                .id(new ObjectId())
                .name(name)
                .description(description)
                .instructions(instructions)
                .type(timing)
                .subScaleType(type)
                .totalTime(time);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
