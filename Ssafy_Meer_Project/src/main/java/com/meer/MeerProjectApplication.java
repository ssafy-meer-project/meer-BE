package com.meer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeerProjectApplication.class, args);
	}
}
