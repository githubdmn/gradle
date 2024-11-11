package com.example.RestBeer01.service.BeerImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.RestBeer01.model.Beer;
import com.example.RestBeer01.service.BeerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerImplementation implements BeerService {

	private Map<UUID, Beer> beerMap;

	BeerImplementation() {

		this.beerMap = new HashMap<>();

		Beer beer1 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name("A beer")
				.style("Ale")
				.upc("123456")
				.price(12.43)
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();

		Beer beer2 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name("B beer")
				.style("Ale")
				.upc("223143432")
				.price(12.43)
				.quantityOnHand(392)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();

		Beer beer3 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name("C beer")
				.style("Lager")
				.upc("3453435")
				.price(12.43)
				.quantityOnHand(214)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();

		this.beerMap.put(beer1.getId(), beer1);
		this.beerMap.put(beer2.getId(), beer2);
		this.beerMap.put(beer3.getId(), beer3);

	}

	@Override
	public Beer getBeerById(UUID id) {
		log.info("Get beer with Id " + id.toString());
		return beerMap.get(id);
	}

	@Override
	public List<Beer> listBeers() {
		return new ArrayList<Beer>(beerMap.values());
	}

	@Override
	public Beer saveBeer(Beer beer) {
		Beer savedBeer = Beer.builder()
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
		log.info("Save beer: ", savedBeer);
		return savedBeer;
	}
}
