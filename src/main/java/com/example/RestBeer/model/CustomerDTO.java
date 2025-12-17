package com.example.RestBeer.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
	
	private UUID id;
	private Integer version;
	
	@NotBlank(message = "Customer name is required")
	private String name;
	
	private LocalDateTime createdDate;
	private LocalDateTime updateDate;
	
	public boolean isEmpty() {
		return false;
	}
}