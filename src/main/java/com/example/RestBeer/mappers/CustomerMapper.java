package com.example.RestBeer.mappers;

import com.example.RestBeer.entities.Customer;
import com.example.RestBeer.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDTO dto);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
}
