/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.response.tests;

import com.futurealgos.micro.testing.models.base.QualitativeDescriptor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Package: com.futurealgos.micro.testing.dto.response.tests
 * Project: Prasad Psycho
 * Created On: 02/09/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@SuperBuilder
public class QualitativeDescriptorInfo {
    public String id;
    public String name;

    public static QualitativeDescriptorInfo build(QualitativeDescriptor qualitativeDescriptor) {
        return QualitativeDescriptorInfo.builder()
                .id(qualitativeDescriptor.getId().toHexString())
                .name(qualitativeDescriptor.getDescription())
                .build();
    }
}
