package com.sunshard.dossier.service.impl;

import com.sunshard.dossier.exception.AttachmentNotCreatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @InjectMocks
    EmailServiceImpl emailService;

    @Mock
    JavaMailSender javaMailSender;

    @Test
    void sendMessage() {
        emailService.sendMessage("to", "subject", "body");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("to");
        message.setSubject("subject");
        message.setText("body");
        Mockito.verify(javaMailSender, Mockito.times(1)).send(message);
    }

    @Test
    void sendMessageWithAttachment() {
        assertThrows(NullPointerException.class,
                () -> emailService.sendMessageWithAttachment(
                        "to", "subject", "body", "docs90"));
        Mockito.verify(javaMailSender, Mockito.times(1)).createMimeMessage();
    }
}