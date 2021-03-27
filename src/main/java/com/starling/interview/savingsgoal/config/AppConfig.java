package com.starling.interview.savingsgoal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "starling.api")
public class AppConfig {
    private String baseUrl;
    private long week;
}
