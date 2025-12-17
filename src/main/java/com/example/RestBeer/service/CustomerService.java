package com.example.RestBeer.service;

import com.example.RestBeer.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
	
	Optional<CustomerDTO> getCustomerById(UUID customerId);
	
	List<CustomerDTO> getAllCustomers();
	
	CustomerDTO saveNewCustomer(CustomerDTO customer);
	
	CustomerDTO updateCustomerById(UUID customerId, CustomerDTO customer);
	
	boolean deleteCustomerById(UUID customerId);
	
	CustomerDTO patchCustomerById(UUID customerId, CustomerDTO customer);
}