package com.sunshard.deal.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(KafkaProducer.class.getName());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Theme theme, EmailMessage message) {

        logger.info("Sending message to topic {}", theme.getName());
        String messageForTopic;
        try {
            messageForTopic = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaTemplate.send(theme.getName(), messageForTopic);
        logger.info("Message sent to topic {}", theme.getName());
    }
}
