package com.example.DependencyInjectionApp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DependencyInjectionApp.service.GreetingA;

public class PropertyInjectedControllerTest {

	PropertyInjectedController controller;

	@BeforeEach
	void setUp() throws Exception {
		controller = new PropertyInjectedController();
		controller.greetingService = new GreetingA();
	}

	@Test
	void testSayHello() {
		System.out.println(controller.sayHello());
	}
}
