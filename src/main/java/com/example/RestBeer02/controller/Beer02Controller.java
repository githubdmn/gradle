package com.example.RestBeer02.controller;


import com.example.RestBeer02.model.Beer02;
import com.example.RestBeer02.service.Beer02Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class Beer02Controller {
	
	public static final String BEER_URL = "/api/v1/beer";
	public static final String BEER_ID = BEER_URL + "/{beerId}";
	
	private final Beer02Service beerService;
	
	@GetMapping(BEER_URL)
	public ResponseEntity<List<Beer02>> listBeers() {
		log.info("Get all beers");
		return ResponseEntity.ok(beerService.listBeers());
	}
	
	@GetMapping("/beers/{id}")
	public ResponseEntity<Beer02> getBeer(@PathVariable UUID id) {
		log.info("Get beer by ID: ", id);
		Beer02 beer = beerService.getBeerById(id);  // Service handles orElseThrow
		return ResponseEntity.ok(beer);
	}
}
