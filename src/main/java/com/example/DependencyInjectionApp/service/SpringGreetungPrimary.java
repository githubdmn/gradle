package com.example.DependencyInjectionApp.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class SpringGreetungPrimary implements GreetingService {

	@Override
	public String sayGreeting() {
		return "\n\n 1. Spring Greeting Service is marked as @Primary \n";
	}
}
