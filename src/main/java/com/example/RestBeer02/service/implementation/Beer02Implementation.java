package com.example.RestBeer02.service.implementation;

import com.example.RestBeer02.exception.NotFoundException;
import com.example.RestBeer02.model.Beer02;

import com.example.RestBeer02.service.Beer02Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Primary
public class Beer02Implementation implements Beer02Service {
	
	private final Map<UUID, Beer02> beerMap;
	
	public Beer02Implementation() {
		
		this.beerMap = new HashMap<>();
		
		Beer02 beer1 = Beer02.builder().id(UUID.randomUUID()).version(1).name("A beer").style("Ale").upc("123456")
				.price(12.43).quantityOnHand(122).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();
		
		Beer02 beer2 = Beer02.builder().id(UUID.randomUUID()).version(1).name("B beer").style("Ale").upc("223143432")
				.price(12.43).quantityOnHand(392).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();
		
		Beer02 beer3 = Beer02.builder().id(UUID.randomUUID()).version(1).name("C beer").style("Lager").upc("3453435")
				.price(12.43).quantityOnHand(214).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();
		
		this.beerMap.put(beer1.getId(), beer1);
		this.beerMap.put(beer2.getId(), beer2);
		this.beerMap.put(beer3.getId(), beer3);
		
	}
	
	@Override
	public Beer02 getBeerById(UUID id) {
		log.info("Get beer with Id " + id.toString());
		return beerMap.get(id);
	}
	
	@Override
	public Optional<Beer02> getBeerByIdOptional(UUID id) {
		log.info("Get beer with Id " + id.toString());
		return Optional.of(beerMap.get(id));
	}
	
	@Override
	public List<Beer02> listBeers() {
		return new ArrayList<Beer02>(beerMap.values());
	}
	
	@Override
	public Beer02 saveBeer(Beer02 beer) {
		Beer02 savedBeer = Beer02
				.builder().id(UUID.randomUUID())
				.version(1).name(beer.getName())
				.style(beer.getStyle())
				.upc(beer.getUpc()).price(beer.getPrice())
				.quantityOnHand(beer.getQuantityOnHand())
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();
		
		beerMap.put(savedBeer.getId(), savedBeer);
		log.info("Save beer: " + savedBeer.toString());
		return savedBeer;
	}
	
	@Override
	public Beer02 updateBeer(UUID beerId, Beer02 beer) {
		Beer02 existing = beerMap.get(beerId);
		existing.setName(beer.getName());
		existing.setStyle(beer.getStyle());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setLastModifiedDate(LocalDateTime.now());
		log.info("Update beer: " + existing.toString());
		beerMap.put(existing.getId(), existing);
		return existing;
	}
	
	@Override
	public void deleteBeer(UUID beerId) {
		beerMap.remove(beerId);
		log.info("Delete beer with Id: " + beerId);
	}
	
	@Override
	public Beer02 patchBeer(UUID beerId, Beer02 beer) {
		log.info("Patch beer with Id: ", beerId);
		Beer02 existing = beerMap.get(beerId);
		if (StringUtils.hasText(beer.getName())) {
			existing.setName(beer.getName());
		}
		if (StringUtils.hasText(beer.getStyle())) {
			existing.setStyle(beer.getStyle());
		}
		if (beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
		}
		if (StringUtils.hasText(beer.getUpc())) {
			existing.setUpc(beer.getUpc());
		}
		if (beer.getQuantityOnHand() != null) {
			existing.setQuantityOnHand(beer.getQuantityOnHand());
		}
		existing.setLastModifiedDate(LocalDateTime.now());
		log.info("Patch beer with Id: " + beerId);
		beerMap.put(existing.getId(), existing);
		log.info("Patch beer: " + existing.toString());
		return existing;
	}
}
