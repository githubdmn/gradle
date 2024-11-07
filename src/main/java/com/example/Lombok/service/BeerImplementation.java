package com.example.Lombok.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.Lombok.model.Beer;

import lombok.extern.slf4j.Slf4j;

@Profile({ "dev", "default" })
@Slf4j
@Service
public class BeerImplementation implements BeerService {

	@Override
	public Beer getBeerById(UUID id) {
		log.debug("Get Beer by Id - in service. Id: " + id.toString());
		return Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.name("Galaxy Cat")
				.style("Pale Ale")
				.upc("12356")
				.price(12.99)
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
	}
}
