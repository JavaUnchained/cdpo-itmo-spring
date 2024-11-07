package com.cdpo.techservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@EnableFeignClients
@SpringBootApplication
public class TechServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechServiceApplication.class, args);
	}

}
