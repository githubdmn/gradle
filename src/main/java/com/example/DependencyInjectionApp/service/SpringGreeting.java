package com.example.DependencyInjectionApp.service;

import org.springframework.stereotype.Service;

@Service
public class SpringGreeting implements GreetingService {

	@Override
	public String sayGreeting() {
		return "Hi Everyone Spring Greeting Service";
	}

}
