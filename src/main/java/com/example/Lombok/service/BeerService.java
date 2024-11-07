package com.example.Lombok.service;

import java.util.UUID;

import com.example.Lombok.model.Beer;

public interface BeerService {

	public Beer getBeerById(UUID id);
}
