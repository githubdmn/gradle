package com.example.RestBeer01.service;

import java.util.List;
import java.util.UUID;

import com.example.RestBeer01.model.Beer;

public interface BeerService {
	public Beer getBeerById(UUID id);

	public List<Beer> listBeers();

	public Beer saveBeer(Beer beer);

	public Beer updateBeer(UUID beerId, Beer beer);

	public void deleteBeer(UUID beerId);

	public Beer patchBeer(UUID beerId, Beer beer);
}
