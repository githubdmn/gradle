package com.example.RestBeer01.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.example.RestBeer01.model.Beer;
import com.example.RestBeer01.service.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerContorller01 {

	private final BeerService beerService;

	// @RequestMapping("/api/v1/beer", method=RequestMethod.GET)
	// @GetMapping("/api/v1/beer")
	@GetMapping() // @RequestMapping(method=RequestMethod.GET)
	public List<Beer> listBeers() {
		log.info("Get all beers 123");
		return beerService.listBeers();
	}

	// @RequestMapping("/api/v1/beer/{beerID}")
	@GetMapping("/{beerId}") // @RequestMapping(method=RequestMethod.GET)
	public String requestMethodName(@PathVariable("beerId") UUID id) {
		log.info("Get beer by ID: ", id);
		return beerService.getBeerById(id).toString();
	}

}
