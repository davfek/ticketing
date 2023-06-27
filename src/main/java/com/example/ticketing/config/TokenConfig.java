package com.example.ticketing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfig {
    @Value("${token.secret-key}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
}
