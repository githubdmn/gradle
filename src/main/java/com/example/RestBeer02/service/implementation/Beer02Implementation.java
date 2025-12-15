package com.example.RestBeer02.service.implementation;

import com.example.RestBeer02.exception.NotFoundException;
import com.example.RestBeer02.model.Beer02DTO;
import com.example.RestBeer02.service.Beer02Service;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Primary
public class Beer02Implementation implements Beer02Service {
	
	private final Map<UUID, Beer02DTO> beerMap;
	
	public Beer02Implementation() {
		this.beerMap = new ConcurrentHashMap<>();
	}
	
	@PostConstruct // marks a method to be executed after dependency injection is complete but before the bean is put into service
	public void init() {
		log.info("Initializing mock beer data");
		
		List<Beer02DTO> initialBeers = Arrays.asList(
				createBeer("A beer", "Ale", "123456", 12.43, 122),
				createBeer("B beer", "Ale", "223143432", 12.43, 392),
				createBeer("C beer", "Lager", "3453435", 12.43, 214)
		);
		
		initialBeers.forEach(beer -> beerMap.put(beer.getId(), beer));
		log.info("Initialized {} beers", beerMap.size());
	}
	
	private Beer02DTO createBeer(String name, String style, String upc,
	                             Double price, Integer quantity) {
		return Beer02DTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name(name)
				.style(style)
				.upc(upc)
				.price(price)
				.quantityOnHand(quantity)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
	}
	
	@Override
	public Beer02DTO getBeerById(UUID beerId) {
		log.debug("Get beer by ID: {}", beerId);
		return Optional.ofNullable(beerMap.get(beerId))
				.orElseThrow(() -> new NotFoundException("Beer not found: " + beerId));
	}
	
	@Override
	public Optional<Beer02DTO> getBeerByIdOptional(UUID beerId) {
		log.debug("Get beer by ID (optional): {}", beerId);
		return Optional.ofNullable(beerMap.get(beerId));
	}
	
	@Override
	public List<Beer02DTO> listBeers() {
		log.debug("List all beers");
		return new ArrayList<>(beerMap.values());
	}
	
	@Override
	public Beer02DTO saveBeer(Beer02DTO beer) {
		Beer02DTO savedBeer = Beer02DTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name(beer.getName())
				.style(beer.getStyle())
				.upc(beer.getUpc())
				.price(beer.getPrice())
				.quantityOnHand(beer.getQuantityOnHand())
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(savedBeer.getId(), savedBeer);
		log.debug("Saved beer: {}", savedBeer);
		return savedBeer;
	}
	
	@Override
	public Beer02DTO updateBeer(UUID beerId, Beer02DTO beer) {
		log.debug("Update beer with ID: {}", beerId);
		
		Beer02DTO existing = beerMap.get(beerId);
		if (existing == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		existing.setName(beer.getName());
		existing.setStyle(beer.getStyle());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setLastModifiedDate(LocalDateTime.now());
		
		log.debug("Updated beer: {}", existing);
		return existing;
	}
	
	@Override
	public void deleteBeer(UUID beerId) {
		log.debug("Delete beer with ID: {}", beerId);
		
		Beer02DTO removed = beerMap.remove(beerId);
		if (removed == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		log.info("Deleted beer: {}", removed.getName());
	}
	
	@Override
	public Beer02DTO patchBeer(UUID beerId, Beer02DTO beer) {
		log.debug("Patch beer with ID: {}", beerId);
		
		Beer02DTO existing = beerMap.get(beerId);
		if (existing == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		boolean modified = false;
		
		if (StringUtils.hasText(beer.getName())) {
			existing.setName(beer.getName());
			modified = true;
		}
		if (StringUtils.hasText(beer.getStyle())) {
			existing.setStyle(beer.getStyle());
			modified = true;
		}
		if (beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
			modified = true;
		}
		if (StringUtils.hasText(beer.getUpc())) {
			existing.setUpc(beer.getUpc());
			modified = true;
		}
		if (beer.getQuantityOnHand() != null) {
			existing.setQuantityOnHand(beer.getQuantityOnHand());
			modified = true;
		}
		
		if (modified) {
			existing.setLastModifiedDate(LocalDateTime.now());
			log.debug("Patched beer: {}", existing);
		} else {
			log.debug("No changes to patch for beer: {}", beerId);
		}
		
		return existing;
	}
	
	// Optional: Add helper method for testing/debugging
	public int getBeerCount() {
		return beerMap.size();
	}
}