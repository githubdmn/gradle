package com.example.RestBeer01.service.BeerImplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.RestBeer01.model.Beer;
import com.example.RestBeer01.service.BeerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerImplementation implements BeerService {

	private Map<UUID, Beer> beerMap;

	public BeerImplementation() {

		this.beerMap = new HashMap<>();

		Beer beer1 = Beer.builder().id(UUID.randomUUID()).version(1).name("A beer").style("Ale").upc("123456")
				.price(12.43).quantityOnHand(122).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();

		Beer beer2 = Beer.builder().id(UUID.randomUUID()).version(1).name("B beer").style("Ale").upc("223143432")
				.price(12.43).quantityOnHand(392).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();

		Beer beer3 = Beer.builder().id(UUID.randomUUID()).version(1).name("C beer").style("Lager").upc("3453435")
				.price(12.43).quantityOnHand(214).createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now()).build();

		this.beerMap.put(beer1.getId(), beer1);
		this.beerMap.put(beer2.getId(), beer2);
		this.beerMap.put(beer3.getId(), beer3);

	}

	@Override
	public Beer getBeerById(UUID id) {
		log.info("Get beer with Id " + id.toString());
		return beerMap.get(id);
	}

	public Optional<Beer> getBeerByIdOptional(UUID id) {
		log.info("Get beer with Id " + id.toString());
		return Optional.of(beerMap.get(id));
	}

	@Override
	public List<Beer> listBeers() {
		return new ArrayList<Beer>(beerMap.values());
	}

	@Override
	public Beer saveBeer(Beer beer) {
		Beer savedBeer = Beer.builder().id(UUID.randomUUID()).version(1).name(beer.getName()).style(beer.getStyle())
				.upc(beer.getUpc()).price(beer.getPrice()).quantityOnHand(beer.getQuantityOnHand())
				.createdDate(LocalDateTime.now()).lastModifiedDate(LocalDateTime.now()).build();

		beerMap.put(savedBeer.getId(), savedBeer);
		log.info("Save beer: ", savedBeer);
		return savedBeer;
	}

	@Override
	public Beer updateBeer(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);
		existing.setName(beer.getName());
		existing.setStyle(beer.getStyle());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
		existing.setLastModifiedDate(LocalDateTime.now());
		log.info("Update beer: ", existing);
		beerMap.put(existing.getId(), existing);
		return existing;
	}

	@Override
	public void deleteBeer(UUID beerId) {
		beerMap.remove(beerId);
		log.info("Delete beer with Id: ", beerId);
	}

	@Override
	public Beer patchBeer(UUID beerId, Beer beer) {
		log.info("Patch beer with Id: ", beerId);
		Beer existing = beerMap.get(beerId);
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
		log.info("Patch beer with Id: ", beerId);
		beerMap.put(existing.getId(), existing);
		log.info("Patch beer: ", existing);
		return existing;
	}
}
