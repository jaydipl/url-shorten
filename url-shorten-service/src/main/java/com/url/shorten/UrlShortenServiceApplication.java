package com.url.shorten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "URL Shortening Service"))
public class UrlShortenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenServiceApplication.class, args);
	}
}
