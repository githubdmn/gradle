package com.example.RestBeer.service;

import com.example.RestBeer.model.BeerDTO;
import com.example.RestBeer.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
	Optional<BeerDTO> getBeerById(UUID id);
	
	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
	                        Integer pageNumber, Integer pageSize);
		
	List<BeerDTO> listBeers();
	
	BeerDTO saveBeer(BeerDTO beer);
	
	BeerDTO updateBeer(UUID beerId, BeerDTO beer);
	
	void deleteBeer(UUID beerId);
	
	BeerDTO patchBeer(UUID beerId, BeerDTO beer);
}
