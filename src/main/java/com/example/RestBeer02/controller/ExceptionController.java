package com.example.RestBeer02.controller;

import com.example.RestBeer02.exception.NotFoundException;
import com.example.RestBeer02.model.Beer02;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Beer02> handleNotFoundException() { // Automatically catches NotFoundException
		log.info("In Beer02 Not Found Exception");
		return ResponseEntity.notFound().build();
	}
	
}