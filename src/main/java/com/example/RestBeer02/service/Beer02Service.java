package com.example.RestBeer02.service;

import com.example.RestBeer02.model.Beer02;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Beer02Service {
	Beer02 getBeerById(UUID id);
	
	Optional<Beer02> getBeerByIdOptional(UUID id);
	
	List<Beer02> listBeers();
	
	Beer02 saveBeer(Beer02 beer);
	
	Beer02 updateBeer(UUID beerId, Beer02 beer);
	
	void deleteBeer(UUID beerId);
	
	Beer02 patchBeer(UUID beerId, Beer02 beer);
}
