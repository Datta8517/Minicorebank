package com.minicorebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class MiniCoreBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniCoreBankApplication.class, args);
	}

}
