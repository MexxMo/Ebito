package ru.digitalmagicians.ebito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EbitoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EbitoApplication.class, args);
    }
}
