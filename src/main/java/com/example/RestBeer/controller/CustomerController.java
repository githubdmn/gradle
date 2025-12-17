package com.example.RestBeer.controller;

import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.model.CustomerDTO;
import com.example.RestBeer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		log.info("Get all customers");
		return ResponseEntity.ok(customerService.getAllCustomers());
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID customerId) {
		log.info("Get customer by ID: {}", customerId);
		return customerService.getCustomerById(customerId)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new NotFoundException("Customer not found: " + customerId));
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
		log.info("Create customer: {}", customerDTO);
		
		CustomerDTO savedCustomer = customerService.saveNewCustomer(customerDTO);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customers/" + savedCustomer.getId());
		
		return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID customerId,
	                                                  @Validated @RequestBody CustomerDTO customerDTO) {
		log.info("Update customer with ID: {}", customerId);
		
		CustomerDTO updatedCustomer = customerService.updateCustomerById(customerId, customerDTO);
		return ResponseEntity.ok(updatedCustomer);
	}
	
	@PatchMapping("/{customerId}")
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable UUID customerId,
	                                                 @RequestBody CustomerDTO customerDTO) {
		log.info("Patch customer with ID: {}", customerId);
		
		CustomerDTO patchedCustomer = customerService.patchCustomerById(customerId, customerDTO);
		return ResponseEntity.ok(patchedCustomer);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
		log.info("Delete customer with ID: {}", customerId);
		
		customerService.deleteCustomerById(customerId);
		return ResponseEntity.noContent().build();
	}
}