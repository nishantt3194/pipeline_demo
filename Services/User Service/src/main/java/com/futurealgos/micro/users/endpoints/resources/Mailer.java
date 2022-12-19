/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.endpoints.resources;

import com.futurealgos.micro.users.dto.payload.MailDto;
import com.futurealgos.micro.users.exceptions.exceptions.InvalidIdentifierFormat;
import com.futurealgos.micro.users.models.base.PreRegister;
import com.futurealgos.micro.users.service.MailService;
import com.futurealgos.micro.users.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mail/")
public class Mailer {
    @Autowired
    MailService mailService;

    @Autowired
    RegistrationService registrationService;

    @PostMapping("welcome/resend")
    public ResponseEntity<String> welcomeMail(@RequestBody List<String> ids) throws InvalidIdentifierFormat {

        List<PreRegister> requests = registrationService.fetchAll(ids);

        int counter = 0;

        for (PreRegister request : requests) {
            boolean sent = mailService.sendWelcomeEmail(request.getId().toHexString(), request.getName(), request.getEmail());
            if(sent) counter++;

        }
        if (counter > 0)
            return ResponseEntity.accepted().body("Verification mail sent successfully to " + counter + " users");
        else
            throw new InvalidIdentifierFormat("Failed to send the mail. Could not find or more registration requests");
    }

    @PostMapping("custom")
    public String customMail(@RequestBody MailDto payload) {
        boolean sent = mailService.sendCustomEmail(payload);
        if (sent)
            return "Mail sent";
        else
            return "Failed to send a mail";
    }
}
