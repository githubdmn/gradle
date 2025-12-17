package com.example.RestBeer;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Log4j2
@SpringBootApplication
public class RestBeerApp {
	
	public static void main(String[] args) {
		runRestBeerApp(args);
	}
	
	public static void runRestBeerApp(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(com.example.RestBeer.RestBeerApp.class, args);
		
		log.info("Rest Beer App starting: {}\n", ctx.getBean(RestBeerApp.class).toString());
		
	}
}
