package com.example.RestBeer.service.impl;

import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.model.BeerDTO;
import com.example.RestBeer.model.BeerStyle;
import com.example.RestBeer.service.BeerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerServiceFixed implements BeerService {
	
	private final Map<UUID, BeerDTO> beerMap;
	
	public BeerServiceFixed() {
		this.beerMap = new ConcurrentHashMap<>();
	}
	
	@PostConstruct
	public void init() {
		log.info("Initializing mock beer data");
		
		List<BeerDTO> initialBeers = Arrays.asList(
				createBeer("IPA", "ALE", "123456", new BigDecimal("6.99"), 122),
				createBeer("Stout", "STOUT", "223143432", new BigDecimal("7.99"), 392),
				createBeer("Pilsner", "LAGER", "3453435", new BigDecimal("5.99"), 214),
				createBeer("Pale Ale", "PALE_ALE", "445566", new BigDecimal("6.49"), 150),
				createBeer("Wheat Beer", "WHEAT", "556677", new BigDecimal("5.49"), 200)
		);
		
		initialBeers.forEach(beer -> beerMap.put(beer.getId(), beer));
		log.info("Initialized {} beers", beerMap.size());
	}
	
	private BeerDTO createBeer(String name, String style, String upc,
	                           BigDecimal price, Integer quantity) {
		return BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName(name)
				.beerStyle(BeerStyle.valueOf(style))
				.upc(upc)
				.price(price)
				.quantityOnHand(quantity)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
	}
	
	@Override
	public Optional<BeerDTO> getBeerById(UUID beerId) {
		log.debug("Get beer by ID: {}", beerId);
		return Optional.ofNullable(beerMap.get(beerId));
	}
	
	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
	                               Integer pageNumber, Integer pageSize) {
		log.debug("List beers with filters: name={}, style={}, showInventory={}",
				beerName, beerStyle, showInventory);
		
		// Convert map values to list
		List<BeerDTO> allBeers = new ArrayList<>(beerMap.values());
		
		// Apply filters
		List<BeerDTO> filteredBeers = allBeers.stream()
				.filter(beer -> {
					boolean matches = true;
					
					// Filter by name (case-insensitive partial match)
					if (StringUtils.hasText(beerName)) {
						matches = beer.getBeerName().toLowerCase()
								.contains(beerName.toLowerCase());
					}
					
					// Filter by style
					if (beerStyle != null) {
						matches = matches && beer.getBeerStyle() == beerStyle;
					}
					
					return matches;
				})
				.collect(Collectors.toList());
		
		// Hide inventory if requested
		if (showInventory != null && !showInventory) {
			filteredBeers.forEach(beer -> beer.setQuantityOnHand(null));
		}
		
		// Create pageable
		int actualPageNumber = pageNumber != null ? Math.max(pageNumber - 1, 0) : 0;
		int actualPageSize = pageSize != null ? Math.min(Math.max(pageSize, 1), 100) : 25;
		
		// Calculate pagination
		int start = actualPageNumber * actualPageSize;
		int end = Math.min(start + actualPageSize, filteredBeers.size());
		
		if (start > filteredBeers.size()) {
			return Page.empty();
		}
		
		List<BeerDTO> pageContent = filteredBeers.subList(start, end);
		Pageable pageable = PageRequest.of(actualPageNumber, actualPageSize);
		
		return new PageImpl<>(pageContent, pageable, filteredBeers.size());
	}
	
	@Override
	public List<BeerDTO> listBeers() {
		log.debug("List all beers");
		return new ArrayList<>(beerMap.values());
	}
	
	@Override
	public BeerDTO saveBeer(BeerDTO beer) {
		log.debug("Save beer: {}", beer);
		
		// Check if UPC already exists
		boolean upcExists = beerMap.values().stream()
				.anyMatch(b -> b.getUpc().equals(beer.getUpc()));
		
		if (upcExists) {
			throw new IllegalArgumentException("Beer with UPC " + beer.getUpc() + " already exists");
		}
		
		BeerDTO savedBeer = BeerDTO.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName(beer.getBeerName())
				.beerStyle(beer.getBeerStyle())
				.upc(beer.getUpc())
				.price(beer.getPrice())
				.quantityOnHand(beer.getQuantityOnHand())
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		
		beerMap.put(savedBeer.getId(), savedBeer);
		log.info("Saved beer with ID: {}", savedBeer.getId());
		return savedBeer;
	}
	
	@Override
	public BeerDTO updateBeer(UUID beerId, BeerDTO beer) {
		log.debug("Update beer with ID: {}", beerId);
		
		BeerDTO existing = beerMap.get(beerId);
		if (existing == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		// Check if new UPC conflicts with other beers
		if (!existing.getUpc().equals(beer.getUpc())) {
			boolean upcExists = beerMap.values().stream()
					.filter(b -> !b.getId().equals(beerId))
					.anyMatch(b -> b.getUpc().equals(beer.getUpc()));
			
			if (upcExists) {
				throw new IllegalArgumentException("UPC " + beer.getUpc() + " already exists");
			}
		}
		
		existing.setBeerName(beer.getBeerName());
		existing.setBeerStyle(beer.getBeerStyle());
		existing.setUpc(beer.getUpc());
		existing.setPrice(beer.getPrice());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setLastModifiedDate(LocalDateTime.now());
		
		log.info("Updated beer with ID: {}", beerId);
		return existing;
	}
	
	@Override
	public boolean deleteBeer(UUID beerId) {
		log.debug("Delete beer with ID: {}", beerId);
		
		BeerDTO removed = beerMap.remove(beerId);
		if (removed == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		log.info("Deleted beer: {} (ID: {})", removed.getBeerName(), beerId);
		return false;
	}
	
	@Override
	public Optional<BeerDTO> patchBeer(UUID beerId, BeerDTO beer) {
		log.debug("Patch beer with ID: {}", beerId);
		
		BeerDTO existing = beerMap.get(beerId);
		if (existing == null) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		boolean modified = false;
		
		if (StringUtils.hasText(beer.getBeerName())) {
			existing.setBeerName(beer.getBeerName());
			modified = true;
		}
		
		if (beer.getBeerStyle() != null) {
			existing.setBeerStyle(beer.getBeerStyle());
			modified = true;
		}
		
		if (StringUtils.hasText(beer.getUpc())) {
			// Check UPC uniqueness
			boolean upcExists = beerMap.values().stream()
					.filter(b -> !b.getId().equals(beerId))
					.anyMatch(b -> b.getUpc().equals(beer.getUpc()));
			
			if (upcExists) {
				throw new IllegalArgumentException("UPC " + beer.getUpc() + " already exists");
			}
			
			existing.setUpc(beer.getUpc());
			modified = true;
		}
		
		if (beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
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
		
		return Optional.of(existing);
	}
	
	// Helper methods
	public int getBeerCount() {
		return beerMap.size();
	}
	
	public void clearAllBeers() {
		beerMap.clear();
		log.info("Cleared all beers");
	}
}