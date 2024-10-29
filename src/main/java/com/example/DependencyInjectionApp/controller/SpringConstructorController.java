package com.example.DependencyInjectionApp.controller;

import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringConstructorController {

	private final GreetingService greetingService; // Spring autowires this

	public SpringConstructorController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		System.out.println("SPRING constructor injected controller");
		return greetingService.sayGreeting();
	}

}
