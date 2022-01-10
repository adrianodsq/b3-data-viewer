package com.adrianodsq.b3data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adrianodsq.b3data")
@EntityScan(basePackages = "com.adrianodsq.b3data")
public class B3DataApplication {

	public static void main(String[] args) {
		SpringApplication.run(B3DataApplication.class, args);
	}

}
