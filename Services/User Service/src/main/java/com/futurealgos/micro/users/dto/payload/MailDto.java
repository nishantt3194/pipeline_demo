/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload;

public class MailDto {
    public String content;
    public String email;
    public String subject;

    public MailDto(String content, String email, String subject) {
        this.content = content;
        this.email = email;
        this.subject = subject;
    }
}
