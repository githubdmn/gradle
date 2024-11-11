package com.example.RestBeer01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestBeerApp01 {

	public static void main(String[] args) {
		runRestBeerApp01(args);
	}

	public static void runRestBeerApp01(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(RestBeerApp01.class, args);

		System.out.println("Rest Beer App starting: " + ctx.getBean(RestBeerApp01.class).toString() + "\n");

	}
}
