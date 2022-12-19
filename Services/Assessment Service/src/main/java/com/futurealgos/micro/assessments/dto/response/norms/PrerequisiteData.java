/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.norms;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.assessments.dto.response
 * Project: Prasad Psycho
 * Created On: 29/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
public class PrerequisiteData implements Serializable {
    public String name;

    public String token;

    public String instructions;

}
