package com.example.Lombok.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;

import com.example.Lombok.model.Beer;
import com.example.Lombok.service.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class LombokController {

	private final BeerService beerService;

	public Beer getBeerById(UUID id) {
		log.debug("Get Beer by Id - in controller. Id: " + id.toString());
		return beerService.getBeerById(id);
	}

	public String sayHello() {
		return "Hello, World! LOMBOK";
	}

}
