package com.example.RestBeer.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
	@Id
	@GeneratedValue(generator = "UUID")
	//'org.hibernate.annotations.GenericGenerator' is deprecated since version 6.5
	// @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
	private UUID id;
	@Version
	private Integer version;
	private String name;
	private String style;
	private String upc;
	private Integer quantityOnHand;
	private Double price;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}