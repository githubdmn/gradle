package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class Myi18nController {

	private final GreetingService greetingService;

	public Myi18nController(@Qualifier("i18nService") GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String sayHello() {
		return greetingService.sayGreeting();
	}
}
