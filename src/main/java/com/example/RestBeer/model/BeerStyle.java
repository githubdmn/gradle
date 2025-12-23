package com.example.RestBeer.model;

import lombok.Getter;

@Getter
public enum BeerStyle {
	LAGER("Lager"),
	PILSNER("Pilsner"),
	STOUT("Stout"),
	PORTER("Porter"),
	ALE("Ale"),
	WHEAT("Wheat Beer"),
	IPA("India Pale Ale"),
	PALE_ALE("Pale Ale"),
	SAISON("Saison"),
	GOSE("Gose"),
	SOUR("Sour Beer");
	
	private final String displayName;
	
	BeerStyle(String displayName) {
		this.displayName = displayName;
	}
	
}