package com.sunshard.deal.service;

import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;

public interface KafkaProducerService {
    void sendMessage(Theme theme, EmailMessage message);
}
