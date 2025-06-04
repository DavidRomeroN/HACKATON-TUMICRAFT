package com.artesanias.infotel;

import com.artesanias.infotel.config.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Slf4j
@EnableConfigurationProperties(CorsProperties.class)
@SpringBootApplication
public class InfotelApplication {

    public static void main(String[] args) {
        log.info("🚀 Starting Virtual Try-On Application...");
        SpringApplication.run(InfotelApplication.class, args);
        log.info("✅ Virtual Try-On Application started successfully!");
        log.info("📚 API Documentation: http://localhost:8080/api/swagger-ui.html");
        log.info("🏥 Health Check: http://localhost:8080/api/health");
    }

}
