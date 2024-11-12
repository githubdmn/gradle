package com.example.RestBeer01.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static com.example.RestBeer01.controller.BeerContorller01.*;
import com.example.RestBeer01.model.Beer;
import com.example.RestBeer01.service.BeerService;
import com.example.RestBeer01.service.BeerImplementation.BeerImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(BeerContorller01.class)
public class BeerContorller01Test {

	@Autowired
	MockMvc mockMvc;

	// #2
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerService;

	BeerImplementation beerImplementation = new BeerImplementation();

	// #3
	@BeforeEach
	void setUp() {
		beerImplementation = new BeerImplementation();
	}

	@Test
	void getBeerById() throws Exception {
		log.info("WARNING: import static ");

		Beer testBeer = beerImplementation.listBeers().get(0);

		// Configure Mockito
		given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

		mockMvc.perform(get(BEER_ID, testBeer.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print()) // This will help debug by printing the response
				.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
				.andExpect(jsonPath("$.name", is(testBeer.getName())));
	}

	@Test
	void testListBeers() throws Exception {
		given(beerService.listBeers()).willReturn(beerImplementation.listBeers());

		mockMvc.perform(get(BEER_URL)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void testSaveBeer() throws Exception {
		// #1.1
		// ObjectMapper objectMapper = new ObjectMapper();
		// #1.2
		// objectMapper.findAndRegisterModules();
		Beer beer = beerImplementation.listBeers().get(0);
		// #1.3
		// System.out.println(objectMapper.writeValueAsString(beer));
		beer.setVersion(null);
		beer.setId(null);
		// #3
		given(beerService.saveBeer((Beer) any(Beer.class)))
				.willReturn(beerImplementation.listBeers().get(1));
		mockMvc
				.perform(post(BEER_URL)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
	}

	@Test // TODO: fix this
	void testUpdateBeer() throws Exception {
		Beer beer = beerImplementation.listBeers().get(0);

		mockMvc.perform(put(BEER_ID, beer.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isNoContent());

		verify(beerService).updateBeer(any(UUID.class), any(Beer.class));
	}

	@Test
	void testDeleteBeer() throws Exception {
		Beer testBeer = beerImplementation.listBeers().get(0);
		UUID beerId = testBeer.getId();

		// given(beerService.deleteBeer(beerId)).willReturn(true);

		mockMvc.perform(delete(BEER_ID, beerId)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(beerService).deleteBeer(beerId);
	}

	@Test
	void testPatchBeer() throws Exception {
		Beer testBeer = beerImplementation.listBeers().get(0);
		UUID beerId = testBeer.getId();

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("name", "New Name");

		// given(beerService.patchBeer(beerId, beerMap)).willReturn(testBeer);

		mockMvc.perform(patch(BEER_ID, beerId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap)))
				.andExpect(status().isOk())
				.andExpect(header().exists("Location"));

		// verify(beerService).patchBeer(beerId, beerMap);
	}

}
