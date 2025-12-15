package com.example.RestBeer02.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Beer02DTO {
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