package com.example.DependencyInjectionApp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DependencyInjectionApp.service.GreetingA;

public class ConstructorInjectedControllerTest {

	ConstructorInjectedController controller;

	@BeforeEach
	void setUp() throws Exception {
		controller = new ConstructorInjectedController(new GreetingA());
	}

	@Test
	void testSayHello() {
		System.out.println("Hello TEST I am CIC - constructor injected controller");
		System.out.println(controller.sayHello());
	}
}
