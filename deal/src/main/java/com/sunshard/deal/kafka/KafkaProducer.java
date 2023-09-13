package com.sunshard.deal.kafka;

import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public void sendMessage(Theme theme, EmailMessage message) {
        System.out.println("SEND TO KAFKA" + theme.getName());
        kafkaTemplate.send(theme.getName(), message);
    }
}
