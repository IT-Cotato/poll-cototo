package org.cotato.poll.polltato.global.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${swagger.base.url:http://localhost:8080}")
	private String baseUrl;

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Polltato API")
				.description("Polltato 투표 서비스 API 문서")
				.version("v1.0.0")
				.contact(new Contact()
					.name("Polltato Team")
					.email("support@polltato.com")))
			.servers(List.of(
				new Server().url(baseUrl).description("서버 URL")
			));
	}
} 
