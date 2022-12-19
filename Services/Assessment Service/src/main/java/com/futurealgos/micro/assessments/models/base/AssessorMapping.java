/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.root.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

/**
 * Package: com.futurealgos.micro.assessments.models.base
 * Project: Prasad Psycho
 * Created On: 26/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
@Setter
@Document(collection = "assessor_mapping")
public class AssessorMapping extends BaseEntity {

    public String reason;

    @DocumentReference(collection = "assessor")
    User assessor;

    @DocumentReference(collection = "assessment_request")
    AssessmentRequest request;

}
