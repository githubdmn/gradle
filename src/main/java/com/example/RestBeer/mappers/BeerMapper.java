package com.example.RestBeer.mappers;

import com.example.RestBeer.entities.Beer;
import com.example.RestBeer.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
		componentModel = "spring",  // Creates Spring Bean
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BeerMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	Beer beerDtoToBeer(BeerDTO dto);
	
	BeerDTO beerToBeerDto(Beer beer);
	
	// For partial updates (PATCH)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	void updateBeerFromDto(BeerDTO dto, @MappingTarget Beer beer);
}