package com.vedalvi.CashFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CashFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashFlowApplication.class, args);
	}

}
