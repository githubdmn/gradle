package com.example.DependencyInjectionApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringQualifierSetterControllerTest {

	@Autowired
	SpringQualifierPropertyController controller;

	@Test
	void testSayHello() {
		System.out.println(controller.SayHello());
	}
}
