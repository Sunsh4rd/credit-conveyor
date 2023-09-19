package com.sunshard.dossier.service;

public interface EmailService {

    void sendMessage(String to, String subject, String body);

    void sendMessageWithAttachment(String to, String subject, String body, String attachment);
}
