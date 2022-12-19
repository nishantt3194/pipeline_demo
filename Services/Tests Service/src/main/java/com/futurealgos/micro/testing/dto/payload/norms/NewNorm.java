/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.norms;

import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.models.embedded.NumericClassification;
import com.futurealgos.micro.testing.models.embedded.RangeClassification;
import com.futurealgos.micro.testing.models.embedded.TextualClassification;
import com.futurealgos.micro.testing.utils.enums.NormTemplate;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@Builder
public record NewNorm(
        @NotNull @NotBlank String name,
        @NotNull List<NewClassification> classifications,
        @NotNull Boolean isPredefined,
        String description,
        NormTemplate template,
        @NotNull Norm.Type type) {

    public Norm convert() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Classification> builtClassifications = new ArrayList<>();
        Constructor<? extends Classification> constructor = getLoader();

        for (NewClassification newClassification : classifications) {
            Classification classification = constructor.newInstance();
            classification.populate(newClassification);
            builtClassifications.add(classification);
        }


        return Norm.builder()
                .name(name)
                .isPredefined(isPredefined)
                .classification(builtClassifications)
                .template(template)
                .type(type)
                .status(StatusEnum.ACTIVE)
                .description(description != null ? description : "")
                .build();
    }

    private Constructor<? extends Classification> getLoader() throws NoSuchMethodException {
        Class<? extends Classification> loader = switch (type) {
            case NUMBER -> NumericClassification.class;
            case RANGE -> RangeClassification.class;
            default -> TextualClassification.class;
        };

        return loader.getConstructor();
    }


}
