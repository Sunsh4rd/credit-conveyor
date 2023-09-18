package com.sunshard.dossier.service;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;

public interface EmailService {

    void sendMessage(String to, String subject, String body);

    void sendMessageWithAttachment(String to, String subject, String body, String attachment) throws MessagingException, FileNotFoundException;
}
