package it.gianotto.crm_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CrmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmProjectApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		String springVersion = SpringBootVersion.getVersion();
		System.out.println("==================================================");
		System.out.println(">> Spring Boot Version: " + springVersion);
		System.out.println(">> START CRM PROJECT NOW");
        System.out.println(">> Copyright 2025 - gianottoroberto@gmail.com");
		System.out.println("==================================================");
	}
}
