package com.liy.parttimesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class PartTimeSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartTimeSystemApplication.class, args);
	}

}
