package com.sunshard.dossier.service;

import com.sunshard.dossier.exception.AttachmentNotCreatedException;
import com.sunshard.dossier.exception.EmailNotCreatedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class.getName());

    @Override
    public void sendMessage(String to, String subject, String body) {
        logger.info("Sending email to {}, subject {}", to, subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        logger.info("Email was sent");
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String body, String attachment) {
        logger.info("Sending email with attachments to {}, subject {}", to, subject);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        FileSystemResource file;
        try {
            file = new FileSystemResource(ResourceUtils.getFile(attachment));
        } catch (FileNotFoundException e) {
            logger.error("Unable to create attachments");
            throw new AttachmentNotCreatedException(e.getMessage());
        }
        try {
            mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            mimeMessageHelper.addAttachment("Documents", file);
        } catch (MessagingException e) {
            logger.error("Unable to create email message");
            throw new EmailNotCreatedException(e.getMessage());
        }
        mailSender.send(message);
        logger.info("Email with attachments was sent");
    }
}
