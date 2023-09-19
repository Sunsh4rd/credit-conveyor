package com.sunshard.dossier.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaConsumerService {
    void listenFinishRegistration(ConsumerRecord<String, String> record);
    void listenCreateDocuments(ConsumerRecord<String, String> record);
    void listenSendDocuments(ConsumerRecord<String, String> record);
    void listenSendSes(ConsumerRecord<String, String> record);
    void listenCreditIssued(ConsumerRecord<String, String> record);
    void listenApplicationDenied(ConsumerRecord<String, String> record);
}
