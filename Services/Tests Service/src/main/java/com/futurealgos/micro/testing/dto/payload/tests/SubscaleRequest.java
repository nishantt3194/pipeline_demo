/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.models.base.Subscale;
import com.futurealgos.micro.testing.utils.enums.EditType;
import com.futurealgos.micro.testing.utils.enums.Mode;
import com.futurealgos.micro.testing.utils.enums.TimingType;
import org.bson.types.ObjectId;

import java.util.List;


public record SubscaleRequest(
        String id,
        EditType opType,
        String name,
        String description,
        String instructions,
        TimingType timing,
        Mode type,
        Integer time,
        List<NewQuestion> questions,
        Boolean toMove,
        String refSubscale) {

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

}
