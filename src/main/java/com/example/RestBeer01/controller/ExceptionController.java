package com.example.RestBeer01.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.RestBeer01.model.Beer;

// 2. Global for all controllers
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Beer> handleNotFoundException() { //AUTOMATICALLY CATCHES IT
		System.out.println("In Beer Not Found Exception");
		return ResponseEntity.notFound().build();
	}

}
