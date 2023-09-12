package com.sunshard.dossier.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    void sendMessage(String to, String subject, String body);
}
