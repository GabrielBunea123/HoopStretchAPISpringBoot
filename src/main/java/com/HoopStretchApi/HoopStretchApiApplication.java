package com.HoopStretchApi;

import com.HoopStretchApi.config.properties.CorsProperties;
import com.HoopStretchApi.config.properties.JwtConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfigProperties.class, CorsProperties.class})
@EnableJpaRepositories(basePackages = "com.HoopStretchApi.repository")
@EntityScan(basePackages = "com.HoopStretchApi.model")
@EnableJpaAuditing
public class HoopStretchApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoopStretchApiApplication.class, args);
    }

}
