package com.sunshard.deal.service;

public interface EmailService {

    void sendMessage(String to, String subject, String body);
}
