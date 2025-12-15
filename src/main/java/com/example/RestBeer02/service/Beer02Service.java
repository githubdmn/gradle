package com.example.RestBeer02.service;

import com.example.RestBeer02.model.Beer02DTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Beer02Service {
	Beer02DTO getBeerById(UUID id);
	
	Optional<Beer02DTO> getBeerByIdOptional(UUID id);
	
	List<Beer02DTO> listBeers();
	
	Beer02DTO saveBeer(Beer02DTO beer);
	
	Beer02DTO updateBeer(UUID beerId, Beer02DTO beer);
	
	void deleteBeer(UUID beerId);
	
	Beer02DTO patchBeer(UUID beerId, Beer02DTO beer);
}
