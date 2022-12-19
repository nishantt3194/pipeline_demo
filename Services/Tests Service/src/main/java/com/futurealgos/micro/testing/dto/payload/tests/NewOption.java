/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.models.embedded.Option;

public record NewOption(
        Integer index,
        String description,
        String tag,
        Integer score) implements Comparable<NewOption> {

    public Option convert() {
        return Option.builder()
                .description(description)
                .index(index)
                .score(score)
                .build();
    }

    @Override
    public int compareTo(NewOption o) {
        return Integer.compare(index, o.index);
    }
}
