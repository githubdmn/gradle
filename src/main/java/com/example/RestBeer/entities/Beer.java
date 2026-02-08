package com.example.RestBeer.entities;

import com.example.RestBeer.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
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
@Table(name = "beer", indexes = {
		@Index(name = "idx_beer_name", columnList = "beer_name"),
		@Index(name = "idx_beer_style", columnList = "beer_style"),
		@Index(name = "idx_upc", columnList = "upc")
})
public class Beer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID id;
	
	@Version
	private Integer version;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "beer_name", nullable = false, length = 100)
	private String beerName;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BeerStyle beerStyle;
	
	@NotBlank
	@Size(max = 50)
	// @Pattern(regexp = "^[0-9]{12,13}$", message = "UPC must be 12 or 13 digits")
	@Column(nullable = false, unique = true, length = 50)
	private String upc;
	
	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;
	
	@Min(0)
	@Column(name = "quantity_on_hand")
	private Integer quantityOnHand;
	
	@CreationTimestamp
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;
	

	public boolean isInStock() {
		return quantityOnHand != null && quantityOnHand > 0;
	}
	
	public boolean isLowStock() {
		return quantityOnHand != null && quantityOnHand < 10;
	}
	
	public void reduceStock(Integer quantity) {
		if (quantityOnHand == null) {
			throw new IllegalStateException("Stock not initialized");
		}
		if (quantityOnHand < quantity) {
			throw new IllegalArgumentException("Insufficient stock");
		}
		this.quantityOnHand -= quantity;
	}
	
	public void increaseStock(Integer quantity) {
		if (quantityOnHand == null) {
			quantityOnHand = 0;
		}
		this.quantityOnHand += quantity;
	}
}