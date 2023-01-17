package com.poll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableScheduling
@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Polls Rest Api", version = "v1"))
public class PollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollApplication.class, args);
	}

}
