package com.example.globalO2O.login.domain.community;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String adminAddress;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public String sendMail(String toAddress, String subject) {
        String authCode = generateAuthCode().toString();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(adminAddress);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText("인증코드: " + authCode);
        javaMailSender.send(simpleMailMessage);

        return authCode;
    }

    public Integer generateAuthCode() {
        Random random = new Random();
        return random.nextInt(888888) + 111111;
    }
}