package com.example.DependencyInjectionApp.service;

import org.junit.jupiter.api.Test;

import com.example.DependencyInjectionApp.controller.MyController;

public class GreetingServiceTest {
	@Test
	void testSayGreeting() {

		MyController myc = new MyController();
		System.out.println(myc.sayHello());
	}
}
