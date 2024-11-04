
package com.example.DependencyInjectionApp.service;

import org.springframework.stereotype.Service;

@Service
// @Service("myGreeting")
public class SpringGreetingQualifier implements GreetingService {

	@Override
	public String sayGreeting() {
		return "\n\n 1. Spring Greeting Service Qualifier \n";
	}
}
