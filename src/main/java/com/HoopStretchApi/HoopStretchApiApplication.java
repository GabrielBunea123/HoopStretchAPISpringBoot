package com.HoopStretchApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.HoopStretchApi.repository")
@EntityScan(basePackages = "com.HoopStretchApi.model")
@EnableJpaAuditing
public class HoopStretchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoopStretchApiApplication.class, args);
    }

}
