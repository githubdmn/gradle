package com.example.RestBeer.repositories;

import com.example.RestBeer.entities.Beer;
import com.example.RestBeer.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
	
	Optional<Beer> findByUpc(String upc);
	
	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
	
	Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);
	
	Page<Beer> findAllByBeerNameContainingIgnoreCase(String beerName, Pageable pageable);
	
	Page<Beer> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
	
	Page<Beer> findAllByQuantityOnHandGreaterThan(Integer quantity, Pageable pageable);
	
	boolean existsByUpc(String upc);
	
	boolean existsByBeerName(String beerName);
	
	@Query("SELECT b FROM Beer b WHERE b.beerStyle = :style AND b.price < :maxPrice")
	Page<Beer> findAffordableByStyle(@Param("style") BeerStyle style,
	                                 @Param("maxPrice") BigDecimal maxPrice,
	                                 Pageable pageable);
}