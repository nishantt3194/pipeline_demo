/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.converters;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.data.convert.TypeInformationMapper;
import org.springframework.data.mapping.Alias;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Package: com.futurealgos.micro.auth.utils.converters
 * Project: Prasad Psycho
 * Created On: 20/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public class EntityInfoMapper implements TypeInformationMapper, BeanClassLoaderAware {

    private final Map<String, Optional<ClassTypeInformation<?>>> cache = new ConcurrentHashMap<>();

    private @Nullable ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public TypeInformation<?> resolveTypeFrom(Alias alias) {
        String stringAlias = alias.mapTyped(String.class);

        if (stringAlias != null) {
            return cache.computeIfAbsent(stringAlias, this::loadClass).orElse(null);
        }

        return null;
    }

    @Override
    public Alias createAliasFor(TypeInformation<?> type) {
        return Alias.of(type.getType().getName());
    }

    private Optional<ClassTypeInformation<?>> loadClass(String typeName) {

        try {
            return Optional.of(ClassTypeInformation.from(ClassUtils.forName(typeName, this.classLoader)));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
