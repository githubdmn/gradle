package com.example.DependencyInjectionApp.controller;

import com.example.DependencyInjectionApp.service.GreetingService;

public class PropertyInjectedController {

	GreetingService greetingService;

	public String sayHello() {
		System.out.println("Hello I'm PIC - property injected controller");
		return greetingService.sayGreeting();
	}
}
