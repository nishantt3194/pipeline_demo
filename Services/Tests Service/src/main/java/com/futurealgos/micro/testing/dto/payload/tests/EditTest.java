/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import com.futurealgos.micro.testing.utils.enums.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;


public record EditTest(
        String id,
        String description,
        String instructions,

        String author,

        String introduction,
        String category,
        AccessLevelEnum accessLevel,
        AdministrationEnum administrationType,
        Integer minAgeMonth,
        Integer maxAgeMonth,
        Integer minAgeYear,
        Integer maxAgeYear,
        TimingType testType,
        Integer totalTime,
        Mode mode,
        HashSet<String> templates,
        String language,
        List<String> norms,
        List<NewQuestion> questions,
        List<SubscaleRequest> subscales,
        NewSubscale defaultSubscale,
        Integer credits,
        String overview,
        EditBlock block) implements Serializable {
}
