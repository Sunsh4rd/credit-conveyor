package com.sunshard.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.dossier.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;

    @KafkaListener(topics = "finish-registration", groupId = "my-group")
    public void listenFinishRegistration(ConsumerRecord<String, String> record) {
//        System.out.println("Key: " + record.key());
//        System.out.println("Value: " + record.value());
        System.out.println(record.key());
        System.out.println(record.value());
        try {
            EmailMessage message = objectMapper.readValue(record.value(), EmailMessage.class);
            System.out.println(message);
            emailService.sendMessage(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "Finish registration for application " + message.getApplicationId() + " by the following link: http://localhost:8081/swagger-ui/index.html#/Deal/calculateCreditData"
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "create-documents", groupId = "my-group")
    public void listenCreateDocuments(ConsumerRecord<String, String> record) {
        try {
            EmailMessage message = objectMapper.readValue(record.value(), EmailMessage.class);
            System.out.println(message);
            emailService.sendMessage(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "Now send creating documents request for application "
                            + message.getApplicationId()
                            + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/sendDocuments"
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "send-documents", groupId = "my-group")
    public void listenSendDocuments(ConsumerRecord<String, String> record) {
        try {
            EmailMessage message = objectMapper.readValue(record.value(), EmailMessage.class);
            System.out.println(message);
            emailService.sendMessage(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "Now send signing documents request for application "
                            + message.getApplicationId()
                            + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/signRequest"
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "send-ses", groupId = "my-group")
    public void listenSendSes(ConsumerRecord<String, String> record) {
        Random random = new Random();
        int sesCode = 1000 + random.nextInt(9000);
        try {
            EmailMessage message = objectMapper.readValue(record.value(), EmailMessage.class);
            System.out.println(message);
            emailService.sendMessage(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "Now provide your SES code " + sesCode + " for application "
                            + message.getApplicationId()
                            + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/signDocuments"
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "credit-issued", groupId = "my-group")
    public void listenCredit(ConsumerRecord<String, String> record) {
        try {
            EmailMessage message = objectMapper.readValue(record.value(), EmailMessage.class);
            System.out.println(message);
            emailService.sendMessage(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "You are now good to go, wait for money"
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
