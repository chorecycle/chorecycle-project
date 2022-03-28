package com.chorecycle.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.chorecycle.model")
public class ChoreCycleRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChoreCycleRestApiApplication.class, args);
	}
}