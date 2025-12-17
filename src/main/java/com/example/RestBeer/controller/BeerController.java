package com.example.RestBeer.controller;

import com.example.RestBeer.service.BeerService;
import com.example.RestBeer.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
	
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
	
	private final BeerService beerService;
	
	@GetMapping(BEER_PATH)
	public ResponseEntity<List<BeerDTO>> getAllBeers() {
		log.info("Get all beers");
		return ResponseEntity.ok(beerService.listBeers());
	}
	
	@GetMapping(BEER_PATH_ID)
	public ResponseEntity<Optional<BeerDTO>> getBeerById(@PathVariable("beerId") UUID beerId) {
		log.info("Get beer by ID: {}", beerId);
		Optional<BeerDTO> beer = beerService.getBeerById(beerId);  // Service throws NotFoundException
		return ResponseEntity.ok(beer);
	}
	
	@PostMapping(BEER_PATH)
	public ResponseEntity<BeerDTO> createBeer(@RequestBody BeerDTO beer) {
		log.info("Create beer: {}", beer);
		BeerDTO savedBeer = beerService.saveBeer(beer);
		
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
	public ResponseEntity<BeerDTO> updateBeer(@PathVariable("beerId") UUID beerId,
	                                            @RequestBody BeerDTO beer) {
		log.info("Update beer with ID: {}", beerId);
		BeerDTO updatedBeer = beerService.updateBeer(beerId, beer);
		
		String location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUriString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", location);
		
		return new ResponseEntity<>(updatedBeer, headers, HttpStatus.OK);
	}
	
	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity<BeerDTO> patchBeer(@PathVariable("beerId") UUID beerId,
	                                           @RequestBody BeerDTO beer) {
		log.info("Patch beer with ID: {}", beerId);
		BeerDTO patchedBeer = beerService.patchBeer(beerId, beer);
		return ResponseEntity.ok(patchedBeer);
	}
	
	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity<Void> deleteBeer(@PathVariable("beerId") UUID beerId) {
		log.info("Delete beer with ID: {}", beerId);
		beerService.deleteBeer(beerId);
		return ResponseEntity.noContent().build();
	}
}