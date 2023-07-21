/*
 * Copyright (c) 2021. Future Algorithms Private Limited.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * All Rights Reserved.
 */

package com.futurealgos.admin.exception.exceptions;


public class TokenExpiredException extends Exception {


    private static final long serialVersionUID = 3469144222595901138L;

    public TokenExpiredException(String message) {
        super(message);
    }
}
