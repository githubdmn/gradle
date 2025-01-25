package com.example.RestBeer01.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 3. for handling the response 
// this eliminates the need for 2. @ControllerAdvice 
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value not found")
public class NotFoundException extends RuntimeException {

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
