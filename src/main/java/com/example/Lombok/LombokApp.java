package com.example.Lombok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.Lombok.controller.LombokController;

@SpringBootApplication
public class LombokApp {

	public static void main(String[] args) {
		runLombok(args);
	}

	public static void runLombok(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LombokApp.class, args);

		LombokController myContoller = ctx.getBean(LombokController.class);

		System.out.println("Lombok " + myContoller.sayHello());

	}
}
