package com.example.librarymanagementsystem.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void send(String to, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Please, confirm you email");
            helper.setFrom("emin@gmail.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email : ", e);
            throw new IllegalStateException("Failed to send email");
        }
    }
}
