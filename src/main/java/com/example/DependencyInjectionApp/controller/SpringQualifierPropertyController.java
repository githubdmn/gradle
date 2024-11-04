package com.example.DependencyInjectionApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.example.DependencyInjectionApp.service.GreetingService;

@Controller
public class SpringQualifierPropertyController {

	@Qualifier("springGreetingQualifier")
	@Autowired
	GreetingService greetingService;

	public String SayHello() {
		return "PROPERTY Qualifier \n" + greetingService.sayGreeting();
	}

}
