/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Package: com.futurealgos.micro.assessments.models.embedded
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Builder
public class AssessmentToken {
    public enum Status {
        ACTIVE, REVOKED, EXPIRED, TERMINATED;
    }

    public String token;

    public Status status;


    public static String generateTokenValue(){
        int length = 8;
        boolean useLetters = true;
        boolean useNumbers = false;

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static AssessmentToken generate() {
        return AssessmentToken.builder()
                .token(generateTokenValue())
                .status(Status.ACTIVE)
                .build();
    }

}
