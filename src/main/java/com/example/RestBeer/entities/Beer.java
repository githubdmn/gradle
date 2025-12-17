package com.example.RestBeer.entities;

import com.example.RestBeer.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "beers")
public class Beer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Version
	private Integer version;
	
	@Column(name = "beer_name", nullable = false)
	private String beerName;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BeerStyle beerStyle;
	
	@Column(nullable = false, unique = true)
	private String upc;
	
	@Column(nullable = false)
	private BigDecimal price;
	
	private Integer quantityOnHand;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime lastModifiedDate;
}