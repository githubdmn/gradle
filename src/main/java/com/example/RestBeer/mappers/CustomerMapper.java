package com.example.RestBeer.mappers;

import com.example.RestBeer.entities.Customer;
import com.example.RestBeer.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "lastModifiedDate", ignore = true)
	Customer customerDtoToCustomer(CustomerDTO dto);
	
	@Mapping(source = "lastModifiedDate", target = "updateDate")
	CustomerDTO customerToCustomerDto(Customer customer);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	void updateCustomerFromDto(CustomerDTO dto, @MappingTarget Customer customer);
}