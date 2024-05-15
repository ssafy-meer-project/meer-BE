package com.meer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI springShopOpenAPI() {
	      return new OpenAPI()
	              .info(new Info()
	            		  .title("Meer Project API")
	            		  .description("Meer Proejct API 입니다.")
	            		  .version("0.0.1")
	            		  .license(new License().name("조민규, 김재현").url("https://www.ssafy.com"))
	              );
	  }
}
