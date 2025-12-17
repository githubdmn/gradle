package com.example.RestBeer.controller;

import com.example.RestBeer.exception.NotFoundException;
import com.example.RestBeer.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class CustomErrorController implements ErrorController {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(
			NotFoundException ex, HttpServletRequest request) {
		
		log.warn("Resource not found: {}", ex.getMessage());
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error("Not Found")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(
			MethodArgumentNotValidException ex, HttpServletRequest request) {
		
		log.warn("Validation error: {}", ex.getMessage());
		
		// Collect field errors
		Map<String, String> fieldErrors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.collect(Collectors.toMap(
						FieldError::getField,
						fieldError -> fieldError.getDefaultMessage() != null
								? fieldError.getDefaultMessage()
								: "Invalid value"
				));
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Validation Failed")
				.message("Input validation failed")
				.path(request.getRequestURI())
				.fieldErrors(fieldErrors)
				.build();
		
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler({TransactionSystemException.class, ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleConstraintViolations(
			Exception ex, HttpServletRequest request) {
		
		log.error("Constraint violation: {}", ex.getMessage());
		
		String message = "Database constraint violation";
		
		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cvEx = (ConstraintViolationException) ex;
			message = cvEx.getConstraintViolations().stream()
					.map(violation ->
							violation.getPropertyPath() + ": " + violation.getMessage())
					.collect(Collectors.joining(", "));
		}
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Constraint Violation")
				.message(message)
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(
			IllegalArgumentException ex, HttpServletRequest request) {
		
		log.warn("Illegal argument: {}", ex.getMessage());
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error("Bad Request")
				.message(ex.getMessage())
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(
			Exception ex, HttpServletRequest request) {
		
		log.error("Unexpected error: {}", ex.getMessage(), ex);
		
		ErrorResponse error = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("Internal Server Error")
				.message("An unexpected error occurred")
				.path(request.getRequestURI())
				.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}