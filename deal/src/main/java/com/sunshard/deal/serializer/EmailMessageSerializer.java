package com.sunshard.deal.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.deal.model.EmailMessage;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EmailMessageSerializer implements Serializer<EmailMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, EmailMessage emailMessage) {
        try {
            if (emailMessage == null) {
                System.out.println("Received null at serializing");
                return null;
            }
            System.out.println("Serializing...");
            return objectMapper.writeValueAsBytes(emailMessage);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error when serializing EmailMessage");
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, EmailMessage data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
