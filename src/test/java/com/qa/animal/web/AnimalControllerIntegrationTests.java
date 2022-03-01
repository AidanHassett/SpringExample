package com.qa.animal.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.animal.domain.Animal;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class AnimalControllerIntegrationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void testCreate() throws Exception {
		Animal testAnimal = new Animal(null, 12, "Chyna");
		String testAnimalJSON = this.mapper.writeValueAsString(testAnimal);
		RequestBuilder req = post("/create").contentType(MediaType.APPLICATION_JSON).content(testAnimalJSON);
		
		Animal createdAnimal = new Animal(1, 12, "Chyna");
		String createdAnimalJSON = this.mapper.writeValueAsString(createdAnimal);
		ResultMatcher checkStatus = status().isCreated();
		ResultMatcher checkBody = content().json(createdAnimalJSON);
		
		this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}
}
