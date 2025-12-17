package com.example.RestBeer.service.impl;

import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.model.CustomerDTO;
import com.example.RestBeer.service.CustomerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CustomerServiceFixed implements CustomerService {
	
	private final Map<UUID, CustomerDTO> customerMap;
	
	public CustomerServiceFixed() {
		this.customerMap = new ConcurrentHashMap<>();
	}
	
	@PostConstruct
	public void init() {
		log.info("Initializing mock customer data");
		
		List<CustomerDTO> initialCustomers = Arrays.asList(
				createCustomer("Customer 1"),
				createCustomer("Customer 2"),
				createCustomer("Customer 3")
		);
		
		initialCustomers.forEach(customer ->
				customerMap.put(customer.getId(), customer));
		
		log.info("Initialized {} customers", customerMap.size());
	}
	
	private CustomerDTO createCustomer(String name) {
		return CustomerDTO.builder()
				.id(UUID.randomUUID())
				.name(name)
				.version(1)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
	}
	
	@Override
	public Optional<CustomerDTO> getCustomerById(UUID customerId) {
		log.debug("Get customer by ID: {}", customerId);
		return Optional.ofNullable(customerMap.get(customerId));
	}
	
	@Override
	public List<CustomerDTO> getAllCustomers() {
		log.debug("Get all customers");
		return new ArrayList<>(customerMap.values());
	}
	
	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customerDto) {
		log.debug("Save new customer: {}", customerDto);
		
		CustomerDTO savedCustomer = CustomerDTO.builder()
				.id(UUID.randomUUID())
				.name(customerDto.getName())
				.version(1)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		customerMap.put(savedCustomer.getId(), savedCustomer);
		log.info("Saved customer with ID: {}", savedCustomer.getId());
		
		return savedCustomer;
	}
	
	@Override
	public CustomerDTO updateCustomerById(UUID customerId, CustomerDTO customerDto) {
		log.debug("Update customer with ID: {}", customerId);
		
		CustomerDTO existing = customerMap.get(customerId);
		if (existing == null) {
			throw new NotFoundException("Customer not found: " + customerId);
		}
		
		existing.setName(customerDto.getName());
		existing.setUpdateDate(LocalDateTime.now());
		existing.setVersion(existing.getVersion() + 1);
		
		log.info("Updated customer with ID: {}", customerId);
		return existing;
	}
	
	@Override
	public boolean deleteCustomerById(UUID customerId) {
		log.debug("Delete customer with ID: {}", customerId);
		
		CustomerDTO removed = customerMap.remove(customerId);
		if (removed == null) {
			throw new NotFoundException("Customer not found: " + customerId);
		}
		
		log.info("Deleted customer: {} (ID: {})", removed.getName(), customerId);
		return false;
	}
	
	@Override
	public CustomerDTO patchCustomerById(UUID customerId, CustomerDTO customerDto) {
		log.debug("Patch customer with ID: {}", customerId);
		
		CustomerDTO existing = customerMap.get(customerId);
		if (existing == null) {
			throw new NotFoundException("Customer not found: " + customerId);
		}
		
		boolean modified = false;
		
		if (StringUtils.hasText(customerDto.getName())) {
			existing.setName(customerDto.getName());
			modified = true;
		}
		
		if (modified) {
			existing.setUpdateDate(LocalDateTime.now());
			existing.setVersion(existing.getVersion() + 1);
			log.debug("Patched customer: {}", existing);
		} else {
			log.debug("No changes to patch for customer: {}", customerId);
		}
		
		return existing;
	}
	
	// Helper methods
	public int getCustomerCount() {
		return customerMap.size();
	}
	
	public void clearAllCustomers() {
		customerMap.clear();
		log.info("Cleared all customers");
	}
}