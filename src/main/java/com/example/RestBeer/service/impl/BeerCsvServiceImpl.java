package com.example.RestBeer.service.impl;

import com.example.RestBeer.model.BeerCSVRecord;
import com.example.RestBeer.service.BeerCsvService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeerCsvServiceImpl implements BeerCsvService {
	@Override
	public List<BeerCSVRecord> convertCSV(File csvFile) {
		try {
			List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
					.withType(BeerCSVRecord.class)
					.build().parse();
			return beerCSVRecords;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
	}
}
