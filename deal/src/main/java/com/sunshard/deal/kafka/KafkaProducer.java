package com.sunshard.deal.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Theme theme, EmailMessage message) {

        System.out.println("SEND TO KAFKA " + theme.getName());
        try {
            kafkaTemplate.send(theme.getName(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
