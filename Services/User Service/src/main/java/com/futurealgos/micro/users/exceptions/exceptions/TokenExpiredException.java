/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.exceptions.exceptions;


public class TokenExpiredException extends Exception {


    private static final long serialVersionUID = 3469144222595901138L;

    public TokenExpiredException(String message) {
        super(message);
    }
}