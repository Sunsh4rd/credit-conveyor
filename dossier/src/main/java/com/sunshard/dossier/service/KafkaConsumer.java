package com.sunshard.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.dossier.client.DealFeignClient;
import com.sunshard.dossier.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailService emailService;
    private final DealFeignClient dealFeignClient;

    private static final Logger logger = LogManager.getLogger(KafkaConsumer.class.getName());

    @KafkaListener(topics = "finish-registration", groupId = "my-group")
    public void listenFinishRegistration(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sending message of finishing registration for application id {}", message.getApplicationId());
        emailService.sendMessage(
                message.getAddress(),
                message.getTheme().getName(),
                "Finish registration for application " + message.getApplicationId() + " by the following link: http://localhost:8081/swagger-ui/index.html#/Deal/calculateCreditData"
        );
    }

    @KafkaListener(topics = "create-documents", groupId = "my-group")
    public void listenCreateDocuments(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sending message of creating documents for application id {}", message.getApplicationId());
        emailService.sendMessage(
                message.getAddress(),
                message.getTheme().getName(),
                "Now send creating documents request for application "
                        + message.getApplicationId()
                        + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/sendDocuments"
        );
    }

    @KafkaListener(topics = "send-documents", groupId = "my-group")
    public void listenSendDocuments(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sending message of sending documents for application id {}", message.getApplicationId());
        String creditData = dealFeignClient.getCreditData(message.getApplicationId()).getBody();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("docs" + message.getApplicationId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(creditData);
        printWriter.close();
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            emailService.sendMessageWithAttachment(
                    message.getAddress(),
                    message.getTheme().getName(),
                    "Your documents are ready and attached to this email. Now send signing documents request for application "
                            + message.getApplicationId()
                            + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/signRequest",
                    "docs" + message.getApplicationId()
            );
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "send-ses", groupId = "my-group")
    public void listenSendSes(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String sesCode = dealFeignClient.getSesCode(message.getApplicationId()).getBody();
        logger.info("Sending message of sending ses code for application id {}", message.getApplicationId());
        emailService.sendMessage(
                message.getAddress(),
                message.getTheme().getName(),
                "Now provide your SES code " + sesCode + " for application "
                        + message.getApplicationId()
                        + " by the following link: http://localhost:8081/swagger-ui/index.html#/Documents/signDocuments"
        );
    }

    @KafkaListener(topics = "credit-issued", groupId = "my-group")
    public void listenCredit(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sending message of issuing credit for application id {}", message.getApplicationId());
        emailService.sendMessage(
                message.getAddress(),
                message.getTheme().getName(),
                "You are now good to go, wait for money"
        );
    }

    @KafkaListener(topics = "application-denied", groupId = "my-group")
    public void listenApplicationDenied(ConsumerRecord<String, String> record) {
        EmailMessage message;
        try {
            message = objectMapper.readValue(record.value(), EmailMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("Sending message of credit denial application {}", message.getApplicationId());
        emailService.sendMessage(
                message.getAddress(),
                message.getTheme().getName(),
                "Your loan application was denied"
        );
    }
}
