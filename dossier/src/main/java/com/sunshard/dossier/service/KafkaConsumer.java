package com.sunshard.dossier.service;

import com.sunshard.dossier.model.EmailMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "finish-registration", groupId = "my-group")
    public void listen(ConsumerRecord<String, EmailMessage> record) {
//        System.out.println("Key: " + record.key());
//        System.out.println("Value: " + record.value());
        System.out.println(record.value());
    }
}
