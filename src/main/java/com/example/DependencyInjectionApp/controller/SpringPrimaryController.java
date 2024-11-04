package com.example.DependencyInjectionApp.controller;

import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringPrimaryController {

	private final GreetingService service;

	public SpringPrimaryController(GreetingService greeting) {
		this.service = greeting;
	}

	public String sayHello() {
		return "SPRING primary controller \n" + this.service.sayGreeting();
	}

}
