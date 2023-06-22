package ru.digitalmagicians.ebito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EbitoApplication {
    public static void main(String[] args) {
        SpringApplication.run(EbitoApplication.class, args);
        log.info("Application started");
    }
}
