package com.example.DependencyInjectionApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.DependencyInjectionApp.service.GreetingA;

public class SetterInjectedControllerTest {

	SetterInjectedController controller;

	@BeforeEach
	void setUp() throws Exception {
		controller = new SetterInjectedController();
		controller.setGreetingService(new GreetingA());
	}

	@Test
	void testSayHello() {
		System.out.println("Hello TEST I am SIC - setter injected controller");
		System.out.println(controller.sayHello());
		String result = controller.sayHello();
		assertEquals("Hi Everyone Greeting A", result, "Expected greeting message does not match");
	}
}
