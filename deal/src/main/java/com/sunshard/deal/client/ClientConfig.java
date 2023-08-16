package com.sunshard.deal.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    
    @Value("${conveyor-address}")
    private String conveyorAddress;
}
