package com.example.RestBeer.service.impl;

import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.model.CustomerDTO;
import com.example.RestBeer.entities.Customer;
import com.example.RestBeer.mappers.CustomerMapper;
import com.example.RestBeer.repositories.CustomerRepository;
import com.example.RestBeer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class CustomerServiceJPA implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	@Override
	@Transactional(readOnly = true)
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		log.debug("Get customer by ID: {}", customerId);
		return customerRepository.findById(customerId)
				.map(customerMapper::customerToCustomerDto);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CustomerDTO> getAllCustomers() {
		log.debug("Get all customers");
		return customerRepository.findAll().stream()
				.map(customerMapper::customerToCustomerDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customerDto) {
		log.debug("Save new customer: {}", customerDto);
		
		// Map DTO to entity
		Customer customer = customerMapper.customerDtoToCustomer(customerDto);
		
		// Save to database
		Customer savedCustomer = customerRepository.save(customer);
		
		// Map back to DTO
		return customerMapper.customerToCustomerDto(savedCustomer);
	}
	
	@Override
	public CustomerDTO updateCustomerById(UUID customerId, CustomerDTO customerDto) {
		log.debug("Update customer with ID: {}", customerId);
		
		// Find existing customer
		Customer existingCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
		
		// Update fields
		existingCustomer.setName(customerDto.getName());
		
		// Save updated customer
		Customer updatedCustomer = customerRepository.save(existingCustomer);
		
		return customerMapper.customerToCustomerDto(updatedCustomer);
	}
	
	@Override
	public boolean deleteCustomerById(UUID customerId) {
		log.debug("Delete customer with ID: {}", customerId);
		
		// Check if customer exists
		if (!customerRepository.existsById(customerId)) {
			throw new NotFoundException("Customer not found: " + customerId);
		}
		
		customerRepository.deleteById(customerId);
		log.info("Deleted customer with ID: {}", customerId);
		return false;
	}
	
	@Override
	public CustomerDTO patchCustomerById(UUID customerId, CustomerDTO customerDto) {
		log.debug("Patch customer with ID: {}", customerId);
		
		// Find existing customer
		Customer existingCustomer = customerRepository.findById(customerId)
				.orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
		
		// Apply partial updates
		if (StringUtils.hasText(customerDto.getName())) {
			existingCustomer.setName(customerDto.getName());
		}
		
		// Save patched customer
		Customer patchedCustomer = customerRepository.save(existingCustomer);
		
		return customerMapper.customerToCustomerDto(patchedCustomer);
	}
	
	// Optional: Additional business methods
	public boolean customerExists(UUID customerId) {
		return customerRepository.existsById(customerId);
	}
	
	public long getCustomerCount() {
		return customerRepository.count();
	}
}