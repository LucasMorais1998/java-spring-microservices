package com.youtube.javaspringmicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JavaSpringMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringMicroservicesApplication.class, args);
	}

}
