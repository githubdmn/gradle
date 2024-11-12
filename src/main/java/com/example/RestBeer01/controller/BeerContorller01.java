package com.example.RestBeer01.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.example.RestBeer01.model.Beer;
import com.example.RestBeer01.service.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerContorller01 {

	public static final String BEER_URL = "/api/v1/beer";
	public static final String BEER_ID = BEER_URL + "/{beerId}";

	private final BeerService beerService;

	// @RequestMapping("/api/v1/beer", method=RequestMethod.GET)
	// @GetMapping("/api/v1/beer")
	@GetMapping(BEER_URL) // @RequestMapping(method=RequestMethod.GET)
	public List<Beer> listBeers() {
		log.info("Get all beers");
		return beerService.listBeers();
	}

	// @RequestMapping("/api/v1/beer/{beerID}")
	@GetMapping(BEER_ID) // @RequestMapping(method=RequestMethod.GET)
	public Beer getBeerById(@PathVariable("beerId") UUID id) {
		log.info("Get beer by ID: ", id);
		return beerService.getBeerById(id);
	}

	@PostMapping(BEER_URL)
	public ResponseEntity<Beer> saveBeer(@RequestBody Beer beer) {
		log.info("Save beer: ", beer);
		Beer beerSaved = beerService.saveBeer(beer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + beerSaved.getId().toString());

		return new ResponseEntity<Beer>(headers, HttpStatus.CREATED);
	}

	@PutMapping(BEER_ID)
	public ResponseEntity<Beer> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
		Beer updatedBeer = beerService.updateBeer(beerId, beer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + beerId.toString());

		return new ResponseEntity<>(updatedBeer, headers, HttpStatus.OK);
	}

	@DeleteMapping(BEER_ID)
	public ResponseEntity<Beer> deleteBeer(@PathVariable("beerId") UUID beerId) {
		beerService.deleteBeer(beerId);
		return new ResponseEntity<Beer>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(BEER_ID)
	public ResponseEntity<Beer> patchBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
		Beer updatedBeer = beerService.patchBeer(beerId, beer);
		return new ResponseEntity<Beer>(updatedBeer, HttpStatus.NO_CONTENT);
	}

}