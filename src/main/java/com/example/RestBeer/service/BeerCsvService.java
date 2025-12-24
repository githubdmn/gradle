package com.example.RestBeer.service;

import com.example.RestBeer.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
	List<BeerCSVRecord> convertCSV(File csvFile);
}
