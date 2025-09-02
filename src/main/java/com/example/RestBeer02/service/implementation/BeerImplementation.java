package com.example.RestBeer02.service.implementation;

import com.example.RestBeer02.service.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BeerImplementation implements Beer {
	
	
	
	@Override
	public com.example.RestBeer01.model.Beer getBeerById(UUID id) {
		return null;
	}
	
	@Override
	public Optional<com.example.RestBeer01.model.Beer> getBeerByIdOptional(UUID id) {
		return Optional.empty();
	}
	
	@Override
	public List<com.example.RestBeer01.model.Beer> listBeers() {
		return List.of();
	}
	
	@Override
	public com.example.RestBeer01.model.Beer saveBeer(com.example.RestBeer01.model.Beer beer) {
		return null;
	}
	
	@Override
	public com.example.RestBeer01.model.Beer updateBeer(UUID beerId, com.example.RestBeer01.model.Beer beer) {
		return null;
	}
	
	@Override
	public void deleteBeer(UUID beerId) {
	
	}
	
	@Override
	public com.example.RestBeer01.model.Beer patchBeer(UUID beerId, com.example.RestBeer01.model.Beer beer) {
		return null;
	}
}
