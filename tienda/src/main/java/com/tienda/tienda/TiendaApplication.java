package com.tienda.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.tienda.tienda.config.ApiUrlsProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApiUrlsProperties.class)
public class TiendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaApplication.class, args);
    }
}
