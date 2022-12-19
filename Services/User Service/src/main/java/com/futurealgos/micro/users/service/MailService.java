/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.service;

import com.futurealgos.micro.users.dto.payload.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    TemplateEngine engine;

    public boolean sendWelcomeEmail(String id, String partnerName, String email) {
        try {
            Context context = new Context();
            context.setVariable("NAME", partnerName);
            context.setVariable("REGISTRATION_LINK", "https://partner.prasadpsycho.industrialdata.in/#/pages/register?id="+id);

            String process = engine.process("verify", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);


            helper.setFrom("no-reply@industrialdata.in", "Prasad Psycho Support");

            String subject = "Welcome! Here's your Registration Link";

            helper.setSubject(subject);
            helper.setText(process, true);
            helper.setTo(email);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean sendCustomEmail(MailDto payload) {
        try {
            Context context = new Context();
            context.setVariable("CONTENT", payload.content);

            String process = engine.process("verify", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("no-reply@industrialdata.in", "Prasad Psycho Support");
            helper.setTo(payload.email);

            helper.setSubject(payload.subject);

            helper.setText(process, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
