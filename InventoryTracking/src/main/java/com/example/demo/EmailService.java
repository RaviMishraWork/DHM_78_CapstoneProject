package com.example.demo;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

@Component
public class EmailService {

        @Autowired
        private JavaMailSender emailSender;

        public void sendSimpleMessage(String from,
                String to, String subject, String text) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }
        public void sendMimeMessage(String from,
                                    String to, String subject, String text) {
            MimeMessage message = emailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(from);
                if (to.contains(",")) {
                    String[] toList = to.split(",");
                    for (String recipient : toList) {
                        helper.addTo(recipient.trim());
                    }
                } else {
                    helper.setTo(to);
                };
                helper.setSubject(subject);
                helper.setText(text, true);
                emailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
