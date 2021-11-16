package com.demo.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.demo.entities")
@EnableJpaRepositories("com.demo.repositories")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(com.demo.spring.Application.class, args);
    }

}
