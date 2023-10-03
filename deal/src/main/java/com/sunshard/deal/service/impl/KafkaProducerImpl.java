package com.sunshard.deal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.deal.exception.MessageNotSentToTopicException;
import com.sunshard.deal.model.EmailMessage;
import com.sunshard.deal.model.enums.Theme;
import com.sunshard.deal.service.KafkaProducerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for sending messages to kafka
 */
@Service
public class KafkaProducerImpl implements KafkaProducerService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LogManager.getLogger(KafkaProducerImpl.class.getName());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Send message to kafka topic
     * @param theme name of the topic
     * @param message sent email data
     */
    @Override
    public void sendMessage(Theme theme, EmailMessage message) {
        logger.info("Sending message to topic {}", theme.getName());
        String messageForTopic;
        try {
            messageForTopic = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new MessageNotSentToTopicException(e.getMessage());
        }
        kafkaTemplate.send(theme.getName(), messageForTopic);
        logger.info("Message sent to topic {}", theme.getName());
    }
}
