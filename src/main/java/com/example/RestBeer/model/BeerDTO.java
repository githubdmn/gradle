package com.example.RestBeer.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
	private UUID id;
	private Integer version;
	private String name;
	private String style;
	private String upc;
	private Integer quantityOnHand;
	private Double price;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}