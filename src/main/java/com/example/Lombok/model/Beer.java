package com.example.Lombok.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Beer {
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
