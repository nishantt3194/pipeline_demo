/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Duration;

@ReadingConverter
public class DurationDeserializer implements Converter<String, Duration> {

    @Override
    public Duration convert(String source) {
        return Duration.parse(source);
    }
}
