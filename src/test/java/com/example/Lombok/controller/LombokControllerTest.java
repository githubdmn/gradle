package com.example.Lombok.controller;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LombokControllerTest {

	@Autowired
	private LombokController lombokController;

	@Test
	void testGetBeerById() {
		System.out.println(lombokController.getBeerById(UUID.randomUUID()));
	}
}
