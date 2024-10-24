package com.example.DependencyInjectionApp.controller;

import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingA;
import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class MyController {

	private final GreetingService greetingService;

	public MyController() {
		this.greetingService = new GreetingA();
	}

	public String sayHello() {
		System.out.println("Hello I'm my contoller");

		return greetingService.sayGreeting();
	}

}
