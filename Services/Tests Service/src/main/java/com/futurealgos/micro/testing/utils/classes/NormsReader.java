/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.utils.classes;


import com.futurealgos.micro.testing.models.base.Norm;
import com.futurealgos.micro.testing.models.embedded.Classification;
import com.futurealgos.micro.testing.models.embedded.NumericClassification;
import com.futurealgos.micro.testing.models.embedded.RangeClassification;
import com.futurealgos.micro.testing.models.embedded.TextualClassification;
import com.futurealgos.micro.testing.utils.enums.NormTemplate;
import com.futurealgos.micro.testing.utils.enums.StatusEnum;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.assessments.utils
 * Project: Prasad Psycho
 * Created On: 28/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@ReadingConverter
public class NormsReader implements Converter<Document, Norm> {
    @Override
    public Norm convert(Document source) {
        String template = source.getString("template");
        String type = source.getString("type");
        String status = source.getString("status");

        Norm norm = Norm.builder()
                .id(source.getObjectId("_id"))
                .name(source.getString("name"))
                .isPredefined(source.getBoolean("isPredefined"))
                .template(template != null && template.length() > 0 ? NormTemplate.valueOf(template) : null)
                .type(type != null && type.length() > 0 ? Norm.Type.valueOf(type) : null)
                .status(status != null && status.length() > 0 ? StatusEnum.valueOf(status) : null)
                .build();

        List<Document> raw = source.getList("classification", Document.class);

        List<Classification> builtClassifications = new ArrayList<>();
        Constructor<? extends Classification> loader;

        try {
            loader = getLoader(norm.getType());
        } catch (NoSuchMethodException e) {
            return null;
        }

        for (Document doc: raw) {
            Classification classification = null;
            try {
                classification = loader.newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                continue;
            }

            classification.populate(doc);
            builtClassifications.add(classification);
        }

        norm.setClassification(builtClassifications);
        return norm;
    }

    private Constructor<? extends Classification> getLoader(Norm.Type type) throws NoSuchMethodException {
        Class<? extends Classification> loader = switch (type) {
            case NUMBER -> NumericClassification.class;
            case RANGE -> RangeClassification.class;
            default -> TextualClassification.class;
        };

        return loader.getConstructor();
    }
}
