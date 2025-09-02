package com.example.RestBeer02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class RestBeer02 {
	
	public static void main(String[] args) {
		run(args);
	}
	
	public static void run(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(RestBeer02.class, args);
		log.info("Rest Beer App starting: {}", ctx.getBean(RestBeer02.class));
	}
}
