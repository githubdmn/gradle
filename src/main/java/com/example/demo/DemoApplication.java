package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.DependencyInjectionApp.DIApp;
import com.example.Lombok.LombokApp;
import com.example.RestBeer01.RestBeerApp01;

@SuppressWarnings("unused")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplication.run(DemoApplication.class, args);

		// This is an example of DI
		// DIApp.main(args);

		// This is an example of Lombok
		// LombokApp.main(args);

		// This is an example of Rest Beer App Basic
		RestBeerApp01.main(args);

	}
}