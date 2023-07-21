/*
 * Copyright (c) 2021. Future Algorithms Private Limited.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

package com.futurealgos.admin.exception.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}
