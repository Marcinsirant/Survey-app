package com.Group.Rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) {
		System.out.println(LocalDateTime.now());
		SpringApplication.run(RestApplication.class, args);
	}

}
