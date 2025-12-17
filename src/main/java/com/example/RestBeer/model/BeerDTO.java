package com.example.RestBeer.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
	
	private UUID id;
	private Integer version;
	
	@NotBlank(message = "Beer name is required")
	private String beerName;
	
	@NotNull(message = "Beer style is required")
	private BeerStyle beerStyle;
	
	@NotBlank(message = "UPC is required")
	private String upc;
	
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be positive")
	private BigDecimal price;
	
	private Integer quantityOnHand;
	
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}