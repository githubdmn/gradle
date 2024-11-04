package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringQualifierConstructorController {

	private final GreetingService greetingService;

	/*
	 * if SpringGreetungQualifier is anotated as @Service("myGreeting") then
	 * 
	 * @Qualifier("myGreeting") // Qualifier must match Service
	 */

	public SpringQualifierConstructorController(
			@Qualifier("springGreetingQualifier") GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String SayHello() {
		return "CONSTRUCTOR Qualifier  \n" + greetingService.sayGreeting();
	}

}
