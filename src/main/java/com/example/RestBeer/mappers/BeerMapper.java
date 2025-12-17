package com.example.RestBeer.mappers;

import com.example.RestBeer.entities.Beer;
import com.example.RestBeer.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
	
	Beer beerDtoToBeer(BeerDTO dto);
	
	BeerDTO beerToBeerDto(Beer beer);
}
