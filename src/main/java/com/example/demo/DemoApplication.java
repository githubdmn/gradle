package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.DependencyInjectionApp.DIApp;

@SuppressWarnings("unused")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(DemoApplication.class, args);

		// This is an example of DI
		DIApp.main(args);
	}
}