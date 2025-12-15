package com.example.RestBeer02.controller;

import com.example.RestBeer02.model.Beer02DTO;
import com.example.RestBeer02.service.Beer02Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor  // Better than @AllArgsConstructor for required dependencies
@RestController
public class Beer02Controller {
	
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
	
	private final Beer02Service beerService;
	
	@GetMapping(BEER_PATH)
	public ResponseEntity<List<Beer02DTO>> getAllBeers() {
		log.info("Get all beers");
		return ResponseEntity.ok(beerService.listBeers());
	}
	
	@GetMapping(BEER_PATH_ID)
	public ResponseEntity<Beer02DTO> getBeerById(@PathVariable("beerId") UUID beerId) {
		log.info("Get beer by ID: {}", beerId);
		Beer02DTO beer = beerService.getBeerById(beerId);  // Service throws NotFoundException
		return ResponseEntity.ok(beer);
	}
	
	@PostMapping(BEER_PATH)
	public ResponseEntity<Beer02DTO> createBeer(@RequestBody Beer02DTO beer) {
		log.info("Create beer: {}", beer);
		Beer02DTO savedBeer = beerService.saveBeer(beer);
		
		// Better way to build location header
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedBeer.getId())
				.toUriString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", location);
		
		return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
	}
	
	@PutMapping(BEER_PATH_ID)
	public ResponseEntity<Beer02DTO> updateBeer(@PathVariable("beerId") UUID beerId,
	                                            @RequestBody Beer02DTO beer) {
		log.info("Update beer with ID: {}", beerId);
		Beer02DTO updatedBeer = beerService.updateBeer(beerId, beer);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUriString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", location);
		
		return new ResponseEntity<>(updatedBeer, headers, HttpStatus.OK);
	}
	
	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity<Beer02DTO> patchBeer(@PathVariable("beerId") UUID beerId,
	                                           @RequestBody Beer02DTO beer) {
		log.info("Patch beer with ID: {}", beerId);
		Beer02DTO patchedBeer = beerService.patchBeer(beerId, beer);
		return ResponseEntity.ok(patchedBeer);
	}
	
	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity<Void> deleteBeer(@PathVariable("beerId") UUID beerId) {
		log.info("Delete beer with ID: {}", beerId);
		beerService.deleteBeer(beerId);
		return ResponseEntity.noContent().build();
	}
}