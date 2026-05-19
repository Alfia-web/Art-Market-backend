package com.example.demo;

import jdk.jfr.Enabled;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = {
        "com.example.demo",
        "controller",
        "repository",
        "entity",
        "exception",
        "service",
        "DTO"
})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "com.example.demo.entity")
@EnableScheduling
public class Main {
	public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
	}
}
