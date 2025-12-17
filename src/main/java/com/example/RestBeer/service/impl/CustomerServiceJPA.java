package com.example.RestBeer.service.impl;

import com.example.RestBeer.service.CustomerService;
import com.example.RestBeer.model.CustomerDTO;
import com.example.RestBeer.repositories.CustomerRepository;
import com.example.RestBeer.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	@Override
	public Optional<CustomerDTO> getCustomerById(UUID uuid) {
		return Optional.ofNullable(customerMapper
				.customerToCustomerDTO(customerRepository.findById(uuid).orElse(null)));
	}
	
	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
				.map(customerMapper::customerToCustomerDTO)
				.collect(Collectors.toList());
	}
	
	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {
		return customerMapper.customerToCustomerDTO(customerRepository
				.save(customerMapper.customerDtoToCustomer(customer)));
	}
	
	@Override
	public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
		
		customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
			foundCustomer.setName(customer.getName());
			atomicReference.set(Optional.of(customerMapper
					.customerToCustomerDTO(customerRepository.save(foundCustomer))));
		}, () -> {
			atomicReference.set(Optional.empty());
		});
		
		return atomicReference.get();
	}
	
	@Override
	public Boolean deleteCustomerById(UUID customerId) {
		if(customerRepository.existsById(customerId)){
			customerRepository.deleteById(customerId);
			return true;
		}
		return false;
	}
	
	@Override
	public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
		AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
		
		customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
			if (StringUtils.hasText(customer.getName())){
				foundCustomer.setName(customer.getName());
			}
			atomicReference.set(Optional.of(customerMapper
					.customerToCustomerDTO(customerRepository.save(foundCustomer))));
		}, () -> {
			atomicReference.set(Optional.empty());
		});
		
		return atomicReference.get();
	}
}