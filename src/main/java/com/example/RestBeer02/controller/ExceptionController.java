package com.example.RestBeer02.controller;

import com.example.RestBeer01.controller.NotFoundException;
import com.example.RestBeer01.model.Beer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Beer> handleNotFoundException() { //AUTOMATICALLY CATCHES IT
		log.info("In Beer Not Found Exception");
		return ResponseEntity.notFound().build();
	}
	
}