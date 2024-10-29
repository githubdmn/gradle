package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringSetterController {

	@Autowired // not recommended
	private GreetingService service;

	public void setService(GreetingService greetingService) {
		this.service = greetingService;
	}

	public String sayHello() {
		return "SPRING setter injected controller " + this.service.sayGreeting();
	}

}
