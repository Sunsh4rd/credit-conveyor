package com.sunshard.dossier.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunshard.dossier.model.EmailMessage;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmailMessageDeserializer implements Deserializer<EmailMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public EmailMessage deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null) {
                System.out.println("Received null at deserializing");
                return null;
            }
            System.out.println("deserializing...");
            return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), EmailMessage.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing to EmailMessage " + new String(bytes, StandardCharsets.UTF_8));
        }
    }

    @Override
    public EmailMessage deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
