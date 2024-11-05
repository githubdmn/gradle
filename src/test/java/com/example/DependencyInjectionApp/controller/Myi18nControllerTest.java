package com.example.DependencyInjectionApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// if there is no profile, it will use the default profile "EN"
@ActiveProfiles("IT")
@SpringBootTest
public class Myi18nControllerTest {

	@Autowired
	Myi18nController myi18nController;

	@Test
	void testSayHello() {
		System.out.println(myi18nController.sayHello());
	}
}
