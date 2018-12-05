package com.gramant.starterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@Import(StarterDemoConfiguration.class)
public class StarterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterDemoApplication.class, args);
    }
}
