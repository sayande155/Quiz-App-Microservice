package com.sd133.quiz_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuizModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizModuleApplication.class, args);
	}

}
