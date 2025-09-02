package com.example.RestBeer02.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Beer {
	com.example.RestBeer01.model.Beer getBeerById(UUID id);
	
	Optional<com.example.RestBeer01.model.Beer> getBeerByIdOptional(UUID id);
	
	List<com.example.RestBeer01.model.Beer> listBeers();
	
	com.example.RestBeer01.model.Beer saveBeer(com.example.RestBeer01.model.Beer beer);
	
	com.example.RestBeer01.model.Beer updateBeer(UUID beerId, com.example.RestBeer01.model.Beer beer);
	
	void deleteBeer(UUID beerId);
	
	com.example.RestBeer01.model.Beer patchBeer(UUID beerId, com.example.RestBeer01.model.Beer beer);
}
