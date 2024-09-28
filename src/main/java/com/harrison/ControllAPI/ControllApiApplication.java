package com.harrison.ControllAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ControllApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControllApiApplication.class, args);
	}

}
