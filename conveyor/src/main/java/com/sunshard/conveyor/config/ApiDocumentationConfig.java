package com.sunshard.conveyor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableSwagger2
//@ConfigurationProperties("app.api")
//@ConditionalOnProperty(name = "app.api.swagger.enable", havingValue = "true")
public class ApiDocumentationConfig {

//    private String version;
//    private String title;
//    private String description;
//    private String basePackage;
//    private String contactName;
//    private String contactEmail;

    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Conveyor microservice")
                        .description("Conveyor microservice APIs")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sunsh4rd")
                                .email("ssoslickk@gmail.com")));
    }

//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "Conveyor microservice",
//                "Conveyor microservice APIs",
//                "1.0.0",
//                "Free to use",
//                new Contact("Sunsh4rd", "localhost:8080", "ssoslickk@gmail.com"),
//                "License",
//                "localhost:8080",
//                Collections.emptyList()
//        );
//    }
}
