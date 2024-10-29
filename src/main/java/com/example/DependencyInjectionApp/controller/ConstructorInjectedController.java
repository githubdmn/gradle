package com.example.DependencyInjectionApp.controller;

import com.example.DependencyInjectionApp.service.GreetingService;

public class ConstructorInjectedController {
	private final GreetingService greetingService;

	public ConstructorInjectedController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		System.out.println("Hello I'm CIC - constructor injected controller");
		return greetingService.sayGreeting();
	}
}