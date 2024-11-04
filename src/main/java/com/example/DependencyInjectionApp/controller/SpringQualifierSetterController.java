package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringQualifierSetterController {

	private GreetingService greetingService;

	@Qualifier("springGreetingQualifier")
	@Autowired
	public void setGreetingService(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	public String SayHello() {
		return "SETTER Qualifier \n" + greetingService.sayGreeting();
	}

}
