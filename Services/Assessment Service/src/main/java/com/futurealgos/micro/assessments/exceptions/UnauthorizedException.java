/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.exceptions;

import java.io.Serial;

public class UnauthorizedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8843606631842700955L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
