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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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
		log.info("Get all beers");
		return beerService.listBeers();
	}

	// @RequestMapping("/api/v1/beer/{beerID}")
	@GetMapping("/{beerId}") // @RequestMapping(method=RequestMethod.GET)
	public String requestMethodName(@PathVariable("beerId") UUID id) {
		log.info("Get beer by ID: ", id);
		return beerService.getBeerById(id).toString();
	}

	@PostMapping()
	public ResponseEntity<Beer> saveBee(@RequestBody Beer beer) {
		log.info("Save beer: ", beer);
		Beer beerSaved = beerService.saveBeer(beer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + beerSaved.getId().toString());

		return new ResponseEntity<Beer>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/{beerId}")
	public ResponseEntity<Beer> updateBee(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
		Beer updatedBeer = beerService.updateBeer(beerId, beer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + updatedBeer.getId().toString());

		return new ResponseEntity<Beer>(headers, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{beerId}")
	public ResponseEntity<Beer> deleteBee(@PathVariable("beerId") UUID beerId) {
		beerService.deleteBeer(beerId);
		return new ResponseEntity<Beer>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{beerId}")
	public ResponseEntity<Beer> patchBeer(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
		Beer updatedBeer = beerService.patchBeer(beerId, beer);
		return new ResponseEntity<Beer>(updatedBeer, HttpStatus.NO_CONTENT);
	}

}
