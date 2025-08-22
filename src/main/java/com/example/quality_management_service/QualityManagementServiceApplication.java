package com.example.quality_management_service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QualityManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualityManagementServiceApplication.class, args);
		System.out.println("\n\nApplication Compiled!!!\n");
	}
}
