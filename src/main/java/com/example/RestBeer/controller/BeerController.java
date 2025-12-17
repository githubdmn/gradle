package com.example.RestBeer.controller;

import com.example.RestBeer.model.BeerDTO;
import com.example.RestBeer.model.BeerStyle;
import com.example.RestBeer.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	private final BeerService beerService;
	
	// GET all beers (simple list - legacy endpoint)
	@GetMapping("/all")
	public ResponseEntity<List<BeerDTO>> getAllBeers() {
		log.info("Get all beers");
		return ResponseEntity.ok(beerService.listBeers());
	}
	
	// GET with pagination and filtering (main endpoint)
	@GetMapping
	public ResponseEntity<Page<BeerDTO>> getBeers(
			@RequestParam(required = false) String beerName,
			@RequestParam(required = false) BeerStyle beerStyle,
			@RequestParam(required = false) Boolean showInventory,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "25") Integer size) {
		
		log.info("Get beers - filters: name={}, style={}, showInventory={}, page={}, size={}",
				beerName, beerStyle, showInventory, page, size);
		
		Page<BeerDTO> beerPage = beerService.listBeers(beerName, beerStyle, showInventory, page, size);
		return ResponseEntity.ok(beerPage);
	}
	
	// GET single beer by ID
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId) {
		log.info("Get beer by ID: {}", beerId);
		
		return beerService.getBeerById(beerId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	// POST create new beer
	@PostMapping
	public ResponseEntity<BeerDTO> createBeer(@Validated @RequestBody BeerDTO beerDTO) {
		log.info("Create beer: {}", beerDTO);
		
		BeerDTO savedBeer = beerService.saveBeer(beerDTO);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + savedBeer.getId());
		
		return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
	}
	
	// PUT full update
	@PutMapping("/{beerId}")
	public ResponseEntity<BeerDTO> updateBeer(@PathVariable UUID beerId,
	                                          @Validated @RequestBody BeerDTO beerDTO) {
		log.info("Update beer with ID: {}", beerId);
		
		BeerDTO updatedBeer = beerService.updateBeer(beerId, beerDTO);
		return ResponseEntity.ok(updatedBeer);
	}
	
	// PATCH partial update
	@PatchMapping("/{beerId}")
	public ResponseEntity<Optional<BeerDTO>> patchBeer(@PathVariable UUID beerId,
	                                                   @RequestBody BeerDTO beerDTO) {
		log.info("Patch beer with ID: {}", beerId);
		
		Optional<BeerDTO> patchedBeer = beerService.patchBeer(beerId, beerDTO);
		return ResponseEntity.ok(patchedBeer);
	}
	
	// DELETE beer
	@DeleteMapping("/{beerId}")
	public ResponseEntity<Void> deleteBeer(@PathVariable UUID beerId) {
		log.info("Delete beer with ID: {}", beerId);
		
		beerService.deleteBeer(beerId);
		return ResponseEntity.noContent().build();
	}
}