package com.pridepoints.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Habilitar o agendamento
public class ApiPridePointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPridePointsApplication.class, args);
	}

}
