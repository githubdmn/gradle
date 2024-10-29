package com.example.DependencyInjectionApp.controller;

import com.example.DependencyInjectionApp.service.GreetingService;

public class SetterInjectedController {
	private GreetingService greetingService;

	public void setGreetingService(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		System.out.println("Hello I'm SIC - setter injected controller");
		return greetingService.sayGreeting();
	}
}
