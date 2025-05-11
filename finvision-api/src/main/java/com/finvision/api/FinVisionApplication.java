package com.finvision.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.finvision")
@EntityScan(basePackages = "com.finvision.core.domain")
@EnableJpaRepositories(basePackages = "com.finvision.core.repository")
public class FinVisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinVisionApplication.class, args);
    }
} 