package com.example.RestBeer.repositories;

import com.example.RestBeer.entities.Beer;
import com.example.RestBeer.model.BeerStyle;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
	
	Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);
	
	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
	
	Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);
	
	boolean existsByUpc(@NotBlank(message = "UPC is required") String upc);
}
