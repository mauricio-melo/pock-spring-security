package com.mmelo.financial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class FinancialApplication {

	public static void main(String[] args) {
		new BCryptPasswordEncoder().encode("123_financial_mef");
		SpringApplication.run(FinancialApplication.class, args);
	}

}
