package com.example.RestBeer.service.impl;

import com.example.RestBeer.entities.Beer;
import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.mappers.BeerMapper;
import com.example.RestBeer.model.BeerDTO;
import com.example.RestBeer.model.BeerStyle;
import com.example.RestBeer.repositories.BeerRepository;
import com.example.RestBeer.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService {
	
	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;  // MapStruct mapper injected
	
	@Override
	public Optional<BeerDTO> getBeerById(UUID id) {
		log.debug("Get Beer by Id - Service. Id: {}", id);
		return beerRepository.findById(id)
				.map(beerMapper::beerToBeerDto);
	}
	
	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
	                               Integer pageNumber, Integer pageSize) {
		log.debug("List Beers with filters - Service");
		
		Pageable pageable = buildPageable(pageNumber, pageSize);
		Page<Beer> beerPage;
		
		if (StringUtils.hasText(beerName) && beerStyle != null) {
			beerPage = beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(
					"%" + beerName + "%", beerStyle, pageable);
		} else if (StringUtils.hasText(beerName)) {
			beerPage = beerRepository.findAllByBeerNameContainingIgnoreCase(
					"%" + beerName + "%", pageable);
		} else if (beerStyle != null) {
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageable);
		} else {
			beerPage = beerRepository.findAll(pageable);
		}
		
		if (showInventory != null && !showInventory) {
			beerPage.forEach(beer -> beer.setQuantityOnHand(null));
		}
		
		return beerPage.map(beerMapper::beerToBeerDto);
	}
	
	@Override
	public List<BeerDTO> listBeers() {
		log.debug("List all Beers - Service");
		return beerRepository.findAll().stream()
				.map(beerMapper::beerToBeerDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public BeerDTO saveBeer(BeerDTO beerDto) {
		log.debug("Save Beer - Service: {}", beerDto);
		
		// Check if beer with same UPC already exists
		if (beerRepository.existsByUpc(beerDto.getUpc())) {
			throw new IllegalArgumentException("Beer with UPC " + beerDto.getUpc() + " already exists");
		}
		
		Beer beer = beerMapper.beerDtoToBeer(beerDto);
		Beer savedBeer = beerRepository.save(beer);
		return beerMapper.beerToBeerDto(savedBeer);
	}
	
	@Override
	public BeerDTO updateBeer(UUID beerId, BeerDTO beerDto) {
		log.debug("Update Beer - Service. Id: {}", beerId);
		
		Beer beer = beerRepository.findById(beerId)
				.orElseThrow(() -> new NotFoundException("Beer not found: " + beerId));
		
		// Update all fields except ID and audit fields
		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle());
		beer.setUpc(beerDto.getUpc());
		beer.setPrice(beerDto.getPrice());
		beer.setQuantityOnHand(beerDto.getQuantityOnHand());
		
		Beer updatedBeer = beerRepository.save(beer);
		return beerMapper.beerToBeerDto(updatedBeer);
	}
	
	@Override
	public boolean deleteBeer(UUID beerId) {
		log.debug("Delete Beer - Service. Id: {}", beerId);
		
		if (!beerRepository.existsById(beerId)) {
			throw new NotFoundException("Beer not found: " + beerId);
		}
		
		beerRepository.deleteById(beerId);
		return false;
	}
	
	@Override
	public Optional<BeerDTO> patchBeer(UUID beerId, BeerDTO beerDto) {
		log.debug("Patch Beer - Service. Id: {}", beerId);
		
		Beer beer = beerRepository.findById(beerId)
				.orElseThrow(() -> new NotFoundException("Beer not found: " + beerId));
		
		// Use MapStruct for partial update
		beerMapper.updateBeerFromDto(beerDto, beer);
		
		Beer updatedBeer = beerRepository.save(beer);
		return Optional.ofNullable(beerMapper.beerToBeerDto(updatedBeer));
	}
	
	// Helper method for pagination
	private Pageable buildPageable(Integer pageNumber, Integer pageSize) {
		int queryPageNumber = pageNumber != null ? Math.max(pageNumber - 1, 0) : 0;
		int queryPageSize = pageSize != null ? Math.min(Math.max(pageSize, 1), 100) : 25;
		
		return PageRequest.of(queryPageNumber, queryPageSize,
				Sort.by(Sort.Order.asc("beerName")));
	}
}