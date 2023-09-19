package com.sunshard.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocumentationConfig {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gateway microservice")
                        .description("Gateway microservice APIs")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sunsh4rd")
                                .email("ssoslickk@gmail.com")));
    }
}
